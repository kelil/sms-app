package com.khalil.sms_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khalil.sms_app.models.MessageBatch;

public interface BatchRepository extends JpaRepository<MessageBatch,Integer> {

}
