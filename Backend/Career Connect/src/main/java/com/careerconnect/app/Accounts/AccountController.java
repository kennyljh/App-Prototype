package com.careerconnect.app.Accounts;

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
            String errorMessage = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
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
            String errorMessage = "Failure: Invalid data: " + request;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        if (usernameRepository.findByUsername(request.getUsername()) != null){
            String errorMessage = "Failure: Username already taken";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        AccountInfoDTO accountInfo = request.getAccountInfo();
        if (accountInfo.getAccountType() == null) {
            String errorMessage = "Failure: Account type is required";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        AccountType accountType;
        try {
            accountType = AccountType.valueOf(accountInfo.getAccountType().toUpperCase());
        } catch (IllegalArgumentException e){
            String errorMessage = "Failure: Invalid account type: " + accountInfo.getAccountType();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        switch (accountType){

            case USER -> {
                Account account = getUSERAccount(request, accountType, accountInfo);
                accountRepository.save(account);
            }
            case COMPANY -> {
                //todo
            }
        }
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", true);
        return ResponseEntity.ok(status);
    }

    private static Account getUSERAccount(CreateAccountDTO request, AccountType accountType, AccountInfoDTO accountInfo) {

        Username username = new Username(request.getUsername());
        Account account = new Account(accountType, username);
        account.setFirstName(accountInfo.getFirstName());
        account.setMiddleName(accountInfo.getMiddleName());
        account.setLastName(accountInfo.getLastName());
        account.setPassword(accountInfo.getPassword());
        account.setEmail(accountInfo.getEmail());
        account.setPhoneNumber(accountInfo.getPhoneNumber());
        return account;
    }
}
