package com.careerconnect.app.CompanyProfiles;

import jakarta.validation.constraints.NotNull;

public class CompanyProfileDTO {

    @NotNull(message = "Company/Brand name cannot be null")
    private String brandName;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
