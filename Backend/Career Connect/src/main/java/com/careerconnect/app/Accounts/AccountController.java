package com.careerconnect.app.Accounts;

import com.careerconnect.app.UserProfiles.UserProfile;
import com.careerconnect.app.UserProfiles.UserProfileDTO;
import com.careerconnect.app.Usernames.Username;
import com.careerconnect.app.Usernames.UsernameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UsernameRepository usernameRepository;

    @GetMapping("/get")
    List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @GetMapping("/get/{username}")
    ResponseEntity<?> getSpecificAccount(@PathVariable String username){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }
        return ResponseEntity.ok(accountRepository.findByUsernameId(specificUsername.getId()));
    }

    @GetMapping("/get/USER")
    List<Account> getUSERAccounts(){
        return accountRepository.findByAccountType(AccountType.USER);
    }

    @GetMapping("/get/COMPANY")
    List<Account> getCOMPANYAccounts(){
        return accountRepository.findByAccountType(AccountType.COMPANY);
    }

    @PostMapping("/create")
    ResponseEntity<?> createAccount(@RequestBody CreateAccountDTO request){

        if (request.getAccountInfo() == null || request.getUsername() == null ||
            request.getUsername().isEmpty()){
            String errorMsg = "Failure: Invalid data";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        if (usernameRepository.findByUsername(request.getUsername()) != null){
            String errorMsg = "Failure: Username already taken";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMsg);
        }

        AccountDTO accountInfo = request.getAccountInfo();
        if (accountInfo.getAccountType() == null) {
            String errorMsg = "Failure: Account type is required";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        AccountType accountType;
        try {
            accountType = AccountType.valueOf(accountInfo.getAccountType().toUpperCase());
        } catch (IllegalArgumentException e){
            String errorMsg = "Failure: Invalid account type: " + accountInfo.getAccountType();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        switch (accountType){

            case USER -> {
                
                if (request.getUserProfileInfo() == null){
                    String errorMsg = "Failure: Required user profile information not found";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
                }
                Account account = createUSERAccount(request.getUsername(), accountType, request.getUserProfileInfo());
                accountRepository.save(account);
            }
            case COMPANY -> {
                //todo
            }
        }
        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }

    private static Account createUSERAccount(String newUsername, AccountType accountType, UserProfileDTO userProfileDTO) {

        Username username = new Username(newUsername);
        Account account = new Account(accountType, username);
        UserProfile userProfile = new UserProfile(
                userProfileDTO.getFirstName(),
                userProfileDTO.getMiddleName(),
                userProfileDTO.getLastName(),
                userProfileDTO.getPassword(),
                userProfileDTO.getEmail(),
                userProfileDTO.getPhoneNumber()
        );
        account.setUserProfile(userProfile);
        return account;
    }
}
