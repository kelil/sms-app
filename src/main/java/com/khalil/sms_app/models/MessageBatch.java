package com.khalil.sms_app.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table
@SQLDelete(sql = "UPDATE batch SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class MessageBatch {

    @Id
    @SequenceGenerator(name = "batch_sequence", sequenceName = "batch_sequence",
     allocationSize = 1)
    @GeneratedValue()

    private Integer id;

    private LocalDateTime messageBatchDate;


    private boolean deleted = Boolean.FALSE;

    public MessageBatch() {

    }

    public MessageBatch(LocalDateTime now) {
        this.messageBatchDate = now;
    }

    public LocalDateTime getMessageBatchDate() {
        return messageBatchDate;
    }

    public void setMessageBatchDate(LocalDateTime messageBatchDate) {
        this.messageBatchDate = messageBatchDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}

