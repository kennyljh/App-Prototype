package com.careerconnect.app.Accounts;

import com.careerconnect.app.CareerClusters.CareerCluster;
import com.careerconnect.app.CompanyProfiles.CompanyProfile;
import com.careerconnect.app.UserProfiles.UserProfile;
import com.careerconnect.app.Usernames.Username;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username_id", nullable = false)
    private Username username;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    @ManyToMany
    @JoinTable(
            name= "account_career_cluster",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "career_cluster_id")
    )
    private Set<CareerCluster> careerClusters;

    public Account(){}

    public Account(AccountType accountType, Username username, String password){
        this.accountType = accountType;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public Set<CareerCluster> getCareerClusters() {
        return careerClusters;
    }

    public void setCareerClusters(Set<CareerCluster> careerClusters) {
        this.careerClusters = careerClusters;
    }
}
