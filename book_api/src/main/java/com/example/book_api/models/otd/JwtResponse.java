package com.example.book_api.models.otd;



public class JwtResponse {

    private String token;
    private Object user;


    public JwtResponse(String token, Object user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
