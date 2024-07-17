package com.example.book_api.models.otd;

import lombok.*;

@Setter
@Getter
@Data
public class MyResponse {
    String message;
    boolean status;
    Object data;

    public MyResponse(String message) {
        this.message = message;
    }

    public MyResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public MyResponse(String message, boolean status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
