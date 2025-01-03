package com.careerconnect.app.UserProfiles;

import com.careerconnect.app.Accounts.Account;
import com.careerconnect.app.Accounts.AccountRepository;
import com.careerconnect.app.Global.ImageDataConverter;
import com.careerconnect.app.Usernames.Username;
import com.careerconnect.app.Usernames.UsernameRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/profile")
public class UserProfileController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UsernameRepository usernameRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/get")
    List<UserProfile> getAllUserProfiles(){
        return userProfileRepository.findAll();
    }

    @GetMapping("/get/{username}")
    ResponseEntity<?> getSpecificProfile(@PathVariable String username){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(accountRepository.findByUsernameId(specificUsername.getId()).getUserProfile());
    }

    @PutMapping("/put/{username}")
    ResponseEntity<?> updateSpecificProfile(@PathVariable String username, @RequestBody @Valid UserProfileDTO request){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }

        UserProfile userProfile = accountRepository.findByUsernameId(specificUsername.getId()).getUserProfile();
        userProfileRepository.save(updateUserProfile(request, userProfile));

        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }

    private UserProfile updateUserProfile(UserProfileDTO request, UserProfile existingUserProfile){

        existingUserProfile.setFirstName(request.getFirstName());
        existingUserProfile.setMiddleName(request.getMiddleName());
        existingUserProfile.setLastName(request.getLastName());
        existingUserProfile.setEmail(request.getEmail());
        existingUserProfile.setPhoneNumber(request.getPhoneNumber());
        existingUserProfile.setProfilePicture(ImageDataConverter.base64ToByteArray(request.getProfilePicture()));
        existingUserProfile.setBiography(request.getBiography());
        existingUserProfile.setBirthDate(request.getBirthDate());
        existingUserProfile.setGender(request.getGender());
        existingUserProfile.setCountry(request.getCountry());
        existingUserProfile.setUniversity(request.getUniversity());
        existingUserProfile.setMajor(request.getMajor());
        return existingUserProfile;
    }
}
