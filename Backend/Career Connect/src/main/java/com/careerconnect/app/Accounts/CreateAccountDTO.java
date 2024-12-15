package com.careerconnect.app.Accounts;

/**
 * Data Transfer Object for account information and username
 */
public class CreateAccountDTO {

    private AccountInfoDTO accountInfoDTO;
    private String username;

    public AccountInfoDTO getAccountInfo() {
        return accountInfoDTO;
    }

    public void setAccountInfo(AccountInfoDTO accountInfoDTO) {
        this.accountInfoDTO = accountInfoDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
