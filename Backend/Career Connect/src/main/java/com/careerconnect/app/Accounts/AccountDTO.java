package com.careerconnect.app.Accounts;

/**
 * Data Transfer Object for account information
 */
public class AccountDTO {

    private String accountType;
    private String password;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
