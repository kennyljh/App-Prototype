package com.careerconnect.app.Universities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    List<University> getAllUniversities(){
        return universityRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

    @GetMapping("/get/{prefix}")
    List<University> getUniversityByPrefix(@PathVariable String prefix){
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return universityRepository.findByNameContaining(prefix, sort);
    }
}
