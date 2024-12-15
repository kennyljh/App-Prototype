package com.careerconnect.app.Accounts;

import com.careerconnect.app.Usernames.Username;
import com.careerconnect.app.Usernames.UsernameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/create")
    String createAccount(@RequestBody CreateAccountDTO request){

        if (request.getAccountInfo() == null || request.getUsername() == null ||
            request.getUsername().isEmpty()){
            return "Failure: Invalid data: " + request;
        }

        AccountInfoDTO accountInfo = request.getAccountInfo();
        if (accountInfo.getAccountType() == null) {
            return "Failure: Account type is required";
        }

        AccountType accountType;
        try {
            accountType = AccountType.valueOf(accountInfo.getAccountType().toUpperCase());
        } catch (IllegalArgumentException e){
            return "Failure: Invalid account type: " + accountInfo.getAccountType();
        }

        Username username = new Username(request.getUsername());
        Account account = new Account(accountType, username);

        accountRepository.save(account);

        return "Success: Account created";
    }
}
