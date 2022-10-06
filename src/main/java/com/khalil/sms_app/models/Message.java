package com.khalil.sms_app.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "message_sequence")

    private Integer id;

    @ManyToOne
    private MessageBatch messageBatch;

    @ManyToOne
    private Employee employee;

    private String phoneNumber;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ManyToOne
    private User user;

    @Column(columnDefinition = "TEXT")
    private String messageContent;

    private Integer status;
    public Message() {

    }
    public Message( String messageContent, Integer status) {
        this.messageContent = messageContent;
        this.status = status;
        
    }

    public Message(MessageBatch messageBatch, String phoneNumber, String userName, User user, String messageContent, Integer status) {
        this.messageBatch = messageBatch;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.userName = userName;
        this.messageContent = messageContent;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public User getUser() {
        return user;
    }

    public MessageBatch getMessageBatch() {
        return messageBatch;
    }

    public void setMessageBatch(MessageBatch messageBatch) {
        this.messageBatch = messageBatch;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", messageBatch='" + getMessageBatch() + "'" +
            ", employee='" + getEmployee() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", user='" + getUser() + "'" +
            ", messageContent='" + getMessageContent() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }



}

