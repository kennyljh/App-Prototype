package com.careerconnect.app.Universities;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/university")
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @GetMapping("/get")
    ResponseEntity<?> getAllUniversities(){

        if ((universityRepository.findAll().isEmpty())){
            String errorMsg = "Not universities found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(universityRepository.findAll(Sort.by(Sort.Order.asc("name"))));
    }

    @GetMapping("/get/{prefix}")
    ResponseEntity<?> getUniversityByPrefix(@PathVariable String prefix){

        if ((universityRepository.findAll().isEmpty())){
            String errorMsg = "Not universities found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return ResponseEntity.ok(universityRepository.findByNameContaining(prefix, sort));
    }
}
