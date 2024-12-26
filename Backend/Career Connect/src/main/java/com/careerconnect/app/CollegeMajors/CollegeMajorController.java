package com.careerconnect.app.CollegeMajors;

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
@RequestMapping(value = "/college/major")
public class CollegeMajorController {

    @Autowired
    CollegeMajorRepository collegeMajorRepository;

    @GetMapping("/get")
    ResponseEntity<?> getAllMajors(){

        if (collegeMajorRepository.findAll().isEmpty()){
            String errorMsg = "No college majors found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(collegeMajorRepository.findAll(Sort.by(Sort.Order.asc("name"))));
    }

    @GetMapping("/get/{prefix}")
    ResponseEntity<?> getMajorsByPrefix(@PathVariable String prefix){

        if (collegeMajorRepository.findAll().isEmpty()){
            String errorMsg = "No college majors found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return ResponseEntity.ok(collegeMajorRepository.findByNameContaining(prefix, sort));
    }
}
