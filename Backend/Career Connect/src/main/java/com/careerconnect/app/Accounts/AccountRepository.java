package com.careerconnect.app.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsernameId(Long username_id);

    List<Account> findByAccountType(AccountType accountType);
}
