package com.careerconnect.app.Usernames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/username")
public class UsernameController {

    @Autowired
    UsernameRepository usernameRepository;

    @GetMapping("/check/{username}")
    ResponseEntity<?> checkUsernameAvailability(@PathVariable String username){

        Username specifiedUsername = usernameRepository.findByUsername(username);

        if (specifiedUsername != null){
            String errorMsg = "Failure: Username already taken";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }
        return ResponseEntity.ok(username);
    }
}
