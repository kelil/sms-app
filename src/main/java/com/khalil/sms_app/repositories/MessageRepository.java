package com.khalil.sms_app.repositories;

import java.util.List;

import com.khalil.sms_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.khalil.sms_app.models.Message;
import com.khalil.sms_app.models.MessageBatch;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findMessageByMessageBatch(MessageBatch batch);
    @Query("select distinct m from Message m left join fetch m.user User where exists (select 1 from m.user u where u in(:users))")
    List<Message> findAllByUser(List<User> users);
}
