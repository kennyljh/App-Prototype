package com.careerconnect.app.CareerClusters;

import com.careerconnect.app.Accounts.Account;
import com.careerconnect.app.Accounts.AccountRepository;
import com.careerconnect.app.Usernames.Username;
import com.careerconnect.app.Usernames.UsernameRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/careerCluster")
public class CareerClusterController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UsernameRepository usernameRepository;

    @Autowired
    CareerClusterRepository careerClusterRepository;

    @GetMapping("/get")
    List<CareerCluster> getAllCareerClusters(){
        return careerClusterRepository.findAll();
    }

    @PostMapping("/post/{username}")
    ResponseEntity<?> addCareerClusters(@PathVariable String username, @RequestBody CareerClusterDTO request){

        Username specificUsername = usernameRepository.findByUsername(username);
        if (specificUsername == null){
            String errorMsg = "Account with username: " + username + " does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        }

        if (request == null){
            String errorMsg = "Failure: Invalid career cluster information";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        Account account = accountRepository.findByUsernameId(specificUsername.getId());
        account.setCareerClusters(request.getSelectedCareerClusters());
        accountRepository.save(account);

        Map<String, Boolean> status = new HashMap<>();
        status.put("success", true);
        return ResponseEntity.ok(status);
    }
}
