package com.careerconnect.app.Usernames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/username")
public class UsernameController {

    @Autowired
    UsernameRepository usernameRepository;

    @GetMapping("/check/{username}")
    Boolean checkUsernameAvailability(@PathVariable String username){

        Username desiredUsername = usernameRepository.findByUsername(username);
        return desiredUsername == null;
    }

}
