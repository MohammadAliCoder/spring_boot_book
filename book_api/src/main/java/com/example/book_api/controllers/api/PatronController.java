package com.example.book_api.controllers.api;

import com.example.book_api.controllers.BaseController;
import com.example.book_api.models.dto.PatronDto;
import com.example.book_api.models.otd.MyResponse;
import com.example.book_api.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/patrons")
public class PatronController extends BaseController {
    @Autowired
    PatronService patronService;

    @GetMapping("")
    public ResponseEntity<?> getPatrons(){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                patronService.getAll()
        ), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPatron(@PathVariable("id")  int id){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                patronService.findById(id)
        ), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid PatronDto patronDto){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                patronService.save(patronDto,getAuth())
        ), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id")  int id,@RequestBody @Valid PatronDto patronDto){
        patronDto.setId(id);
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                patronService.save(patronDto,getAuth())
        ), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id")  int id){
        patronService.deleteById(id);
        return new ResponseEntity<>(new MyResponse("Successfully", true), HttpStatus.OK);
    }
}
