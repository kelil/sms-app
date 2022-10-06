package com.khalil.sms_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.khalil.sms_app.models.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import com.khalil.sms_app.models.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    @Query("select distinct u from User u left join fetch u.employee Employee where exists (select 1 from u.employee.division d where d in(:division))")
    List<User> findAllUserByEmployeeDivision(List<Division> division);
}
