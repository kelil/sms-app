package com.khalil.sms_app.payload.response;

import java.util.List;

import org.springframework.http.ResponseCookie;

public class UserInfoResponse {

    private Integer id;
	private String username;
	private String email;
	private List<String> roles;
	private ResponseCookie jwtCookie;

    public UserInfoResponse(Integer id, String username, String email, List<String> roles, ResponseCookie jwtCookie) {
        this.id =id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.jwtCookie = jwtCookie;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public ResponseCookie getJwtCookie() {
        return jwtCookie;
    }

    public void setJwtCookie(ResponseCookie jwtCookie) {
        this.jwtCookie = jwtCookie;
    }

    

}
