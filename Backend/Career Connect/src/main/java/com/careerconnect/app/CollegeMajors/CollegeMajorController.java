package com.careerconnect.app.CollegeMajors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    List<CollegeMajor> getAllMajors(){
        return collegeMajorRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

    @GetMapping("/get/{prefix}")
    List<CollegeMajor> getMajorsByPrefix(@PathVariable String prefix){
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return collegeMajorRepository.findByNameContaining(prefix, sort);
    }
}
