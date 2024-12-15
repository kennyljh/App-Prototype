package com.careerconnect.app.Usernames;

import com.careerconnect.app.Accounts.Account;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsernameRepository extends JpaRepository<Username, Long> {

    Username findByUsername (String username);
}
