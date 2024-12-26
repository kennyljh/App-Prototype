package com.example.careerconnect.SingletonRepository;

/**
 * Singleton repository for storing account related data
 */
public class IdentifyingDataRepository {

    private static IdentifyingDataRepository identifyingDataRepository;

    private CompanyProfile companyProfile;
    private UserProfile userProfile;
    private String accountType;

    public static IdentifyingDataRepository getInstance(){

        if (identifyingDataRepository == null){
            identifyingDataRepository = new IdentifyingDataRepository();
        }
        return identifyingDataRepository;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
