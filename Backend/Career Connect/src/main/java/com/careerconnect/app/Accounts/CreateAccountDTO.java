package com.careerconnect.app.Accounts;

import com.careerconnect.app.CompanyProfiles.CompanyProfileDTO;
import com.careerconnect.app.UserProfiles.UserProfileDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for account information and username
 *
 * Note: Currently the process of creating a new account requires
 * the Client to send both Account and Username information.
 *
 * Issue: We can't have the parameter of the PostMapping
 * for account creation to expect an Account type, Username
 * type or both.
 *
 * Resolution: We create a Data Transfer Object (DTO) to contain
 * information on both Account and Username, and retrieve them
 * accordingly when needed.
 * The Jackson library is used alongside with the @RequestBody
 * annotation to deserialize the JSON payload into the
 * CreateAccountDTO object. Automatically creating an empty
 * instance of CreateAccountDTO, calling getters and setters
 * to populate the fields based on the JSON payload.
 */
public class CreateAccountDTO {

    @Valid
    private AccountDTO accountInfo;

    @NotNull(message = "Desired username cannot be null")
    private String username;

    @Valid
    private UserProfileDTO userProfileInfo;

    @Valid
    private CompanyProfileDTO companyProfileInfo;

    public AccountDTO getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountDTO accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserProfileDTO getUserProfileInfo() {
        return userProfileInfo;
    }

    public void setUserProfileInfo(UserProfileDTO userProfileInfo) {
        this.userProfileInfo = userProfileInfo;
    }

    public CompanyProfileDTO getCompanyProfileInfo() {
        return companyProfileInfo;
    }

    public void setCompanyProfileInfo(CompanyProfileDTO companyProfileInfo) {
        this.companyProfileInfo = companyProfileInfo;
    }
}
