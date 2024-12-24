package com.careerconnect.app.CompanyProfiles;

import com.careerconnect.app.Accounts.AccountRepository;
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
@RequestMapping(value = "/company")
public class CompanyProfileController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UsernameRepository usernameRepository;

    @Autowired
    CompanyProfileRepository companyProfileRepository;

    @GetMapping("/get")
    List<CompanyProfile> getAllCompanyProfiles() {
        return companyProfileRepository.findAll();
    }

    @GetMapping("/get/{username}")
    ResponseEntity<?> getSpecificProfile(@PathVariable String username) {

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null) {
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(accountRepository.findByUsernameId(specificUsername.getId()).getCompanyProfile());
    }

    @PutMapping("/put/{username}")
    ResponseEntity<?> updateSpecificProfile(@PathVariable String username, @RequestBody @Valid CompanyProfileDTO request) {

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null) {
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }

        CompanyProfile companyProfile = accountRepository.findByUsernameId(specificUsername.getId()).getCompanyProfile();
        companyProfileRepository.save(updateCompanyProfile(request, companyProfile));

        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }

    private CompanyProfile updateCompanyProfile(CompanyProfileDTO request, CompanyProfile existingCompanyProfile) {

        existingCompanyProfile.setBrandName(request.getBrandName());
        existingCompanyProfile.setPhoneNumber(request.getPhoneNumber());
        existingCompanyProfile.setEmail(request.getEmail());
        return existingCompanyProfile;
    }
}