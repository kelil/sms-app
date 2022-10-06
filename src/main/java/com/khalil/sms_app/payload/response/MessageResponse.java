package com.khalil.sms_app.payload.response;

public class MessageResponse {

    private String message;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResponse(String message) {
        this.message = message;
       
    }

    public MessageResponse(String message, Integer id) {
        this.message = message;
        this.id = id;
    }

}
