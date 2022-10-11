package com.khalil.sms_app.controllers;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.models.DivisionDTO;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.repositories.DivisionRepository;
import com.khalil.sms_app.services.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://10.1.11.145:4200", maxAge = 360, allowCredentials = "true")
@RestController
@RequestMapping(path = "api/v1/divisions")
public class DivisionController {

    @Autowired
    private DivisionService divisionService;
    private final DivisionRepository divisionRepository;

    public DivisionController(DivisionService divisionService, DivisionRepository divisionRepository) {
        this.divisionService = divisionService;
        this.divisionRepository = divisionRepository;
    }

    @GetMapping()
    public List<Division> getAllDivision(){
        return divisionService.getAllDivision();
    }

    @GetMapping("/division/{id}")
    public ResponseEntity<DivisionDTO> getDivisionsDetails(@PathVariable(value = "id") Integer id){
        return divisionRepository.findById(id).map(mapToDivisionDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/division/{id}/sublings")
    public ResponseEntity<Set<DivisionDTO>> getDivisionsSublings(@PathVariable(value = "id") Integer id){

        return divisionRepository.findById(id).map(findSiblings).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/division")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addDivision(@RequestBody Division division){
        return divisionService.addDivision(division);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteDivision(@PathVariable(value = "id") Integer id){
      divisionRepository.deleteById(id);
      return ResponseEntity.ok().body(new MessageResponse("Deleted SuccessFully"));
    }

    private Function<Division, DivisionDTO> mapToDivisionDTO = d -> DivisionDTO.builder().id(d.getId()).name(d.getName()).parent(d.getParent()).children(d.getChildren()).build();

    private Function<Division, Set<DivisionDTO>> findSiblings = division -> division.getParent().getChildren().stream()
            .map(d -> DivisionDTO.builder().id(d.getId()).name(d.getName()).build()).collect(Collectors.toSet());
}

