package com.khalil.sms_app.services;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.repositories.DivisionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<Division> getAllDivision() {
        return divisionRepository.findAll();
    }

    public ResponseEntity<?> addDivision(Division division) {

        divisionRepository.save(division);
        return ResponseEntity.ok().body(new MessageResponse("Division Added SuccessFully"));

    }

}
