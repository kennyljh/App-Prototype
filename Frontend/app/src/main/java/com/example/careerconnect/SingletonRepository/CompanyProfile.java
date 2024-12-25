package com.example.careerconnect.SingletonRepository;

public class CompanyProfile {

    private String brandName, username, email, phoneNumber;

    public CompanyProfile(){}

    public CompanyProfile(String brandName, String username, String email,
                          String phoneNumber){

        this.brandName = brandName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
