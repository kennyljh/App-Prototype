package com.careerconnect.app.Qualifications;

import com.careerconnect.app.Accounts.AccountRepository;
import com.careerconnect.app.UserProfiles.UserProfile;
import com.careerconnect.app.UserProfiles.UserProfileRepository;
import com.careerconnect.app.Usernames.Username;
import com.careerconnect.app.Usernames.UsernameRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/qualification")
public class QualificationController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UsernameRepository usernameRepository;

    @Autowired
    QualificationRepository qualificationRepository;

    @GetMapping("/get")
    List<Qualification> getAllQualifications(){
        return qualificationRepository.findAll();
    }

    @GetMapping("/get/{username}")
    ResponseEntity<?> getSpecificQualifications(@PathVariable String username){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(accountRepository.findByUsernameId(specificUsername.getId()).getUserProfile().getQualifications());
    }

    @PostMapping("/post/{username}")
    ResponseEntity<?> addNewQualification(@PathVariable String username, @RequestBody @Valid QualificationDTO request){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }

        UserProfile userProfile = accountRepository.findByUsernameId(specificUsername.getId()).getUserProfile();
        qualificationRepository.save(addNewQualification(userProfile, request));

        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/delete/{username}")
    ResponseEntity<?> deleteExistingQualification(@PathVariable String username, @RequestBody @Valid QualificationDTO request){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }

        UserProfile userProfile = accountRepository.findByUsernameId(specificUsername.getId()).getUserProfile();
        qualificationRepository.deleteById(request.getId());

        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }

    private Qualification addNewQualification(UserProfile existingUserProfile, QualificationDTO request){

        Qualification qualification = new Qualification(
                request.getTitle(),
                request.getDescription(),
                request.getDuration()
        );
        qualification.setUserProfile(existingUserProfile);

        return qualification;
    }
}





















