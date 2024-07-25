package com.example.book_api.controllers;

import com.example.book_api.configurations.TokenUtil;
import com.example.book_api.controllers.api.PatronController;
import com.example.book_api.models.dto.PatronDto;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.models.entities.Role;
import com.example.book_api.models.entities.User;
import com.example.book_api.models.entities.UserDetailsImpl;
import com.example.book_api.services.PatronService;
import com.example.book_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(PatronController.class)
@AutoConfigureMockMvc
public class PatronControllerTest  {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatronService patronService;
    @MockBean
    private UserService userService;
    @MockBean
    private TokenUtil tokenUtil;
    private User userAuth;
    private ObjectMapper mapper;
    private String token;
    private Patron patron;
    private List<Patron> patrons;


    @BeforeEach
    public void setup() {
        userAuth = new User(1, "Mohammad", "mohammad@gmail.com", "12345678", true);
        userAuth.setRoles(new HashSet<Role>(Arrays.asList(new Role(1, "User"))));
        UserDetailsImpl userDetails = new UserDetailsImpl(userAuth);
        token = tokenUtil.generateToken(userDetails);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        patron = new Patron(1, "Mohammad Ali", "mohammad@gmail.com", "+96911111111", "Damascus");
        patron.setUser(userAuth);
        userAuth.setPatron(patron);
        patrons = Arrays.asList(patron,
                new Patron(2, "Ali", "ali@gmail.com", "+96922222222", "Damascus")
        );
    }

    @Test
    @Order(1)
    @DisplayName("Check all patrons")
    void getAllPatronsTest() throws Exception {
        given(patronService.getAll()).willReturn(patrons);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data.[0].id", is(1)))
                .andExpect(jsonPath("$.data.[0].email", is("mohammad@gmail.com")))
                .andExpect(jsonPath("$.data.[1].id", is(2)))
                .andExpect(jsonPath("$.data.[1].email", is("ali@gmail.com")));
    }

    @Test
    @Order(2)
    @DisplayName("Check get patron by id")
    void getPatronTest() throws Exception {
        given(patronService.findById(1)).willReturn(patron);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/patrons/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.email", is("mohammad@gmail.com")))
        ;
    }

    @Test
    @Order(3)
    @DisplayName("Check create patron")
    void createPatronTest() throws Exception {
        given(userService.findByEmail(userAuth.getEmail())).willReturn(userAuth);
        given(patronService.save(PatronDto.to_Dto(patron), userAuth)).willReturn(patron);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PatronDto.to_Dto(patron)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.email", is("mohammad@gmail.com")))
        ;
    }

    @Test
    @Order(4)
    @DisplayName("Check update patron")
    void updatePatronTest() throws Exception {
        patron.setEmail("mohammadali@gmail.com");
        given(userService.findByEmail(userAuth.getEmail())).willReturn(userAuth);
        given(patronService.save(PatronDto.to_Dto(patron),userAuth)).willReturn(patron);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/patrons/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PatronDto.to_Dto(patron)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.email", is("mohammadali@gmail.com")))
        ;
    }


    @Test
    @Order(5)
    @DisplayName("Check delete patron by id")
    void deleteBookTest() throws Exception {
        doNothing().when(patronService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/patrons/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.message", is("Successfully")))
        ;
    }

}
