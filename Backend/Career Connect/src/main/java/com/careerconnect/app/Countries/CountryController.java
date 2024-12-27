package com.careerconnect.app.Countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/country")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;

    @GetMapping("/get")
    ResponseEntity<?> getAllCountries(){

        if (countryRepository.findAll().isEmpty()){
            String errorMsg = "No countries found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(countryRepository.findAll(Sort.by(Sort.Order.asc("name"))));
    }

    @GetMapping("/get/{prefix}")
    ResponseEntity<?> getCountryByPrefix(@PathVariable String prefix){

        if (countryRepository.findAll().isEmpty()){
            String errorMsg = "No countries found in repository";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return ResponseEntity.ok(countryRepository.findByNameContaining(prefix, sort));
    }
}
