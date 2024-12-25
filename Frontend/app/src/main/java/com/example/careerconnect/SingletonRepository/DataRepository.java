package com.example.careerconnect.SingletonRepository;

/**
 * Singleton repository for storing account related data
 */
public class DataRepository {

    private static DataRepository dataRepository;

    private CompanyProfile companyProfile;
    private UserProfile userProfile;

    public static DataRepository getInstance(){

        if (dataRepository == null){
            dataRepository = new DataRepository();
        }
        return dataRepository;
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
}
