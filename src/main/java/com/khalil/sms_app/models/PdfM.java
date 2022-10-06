package com.khalil.sms_app.models;

public class PdfM {
    private String sender;
    private Integer total;
    private Double price;

    public PdfM(String sender, Integer total, Double price) {
        this.sender = sender;
        this.total = total;
        this.price = price;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
