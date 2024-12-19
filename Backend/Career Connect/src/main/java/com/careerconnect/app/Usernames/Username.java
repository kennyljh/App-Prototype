package com.careerconnect.app.Usernames;

import com.careerconnect.app.Accounts.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "username")
public class Username {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "username")
    @JsonIgnore
    private Account account;

    @Column(nullable = false, unique = true)
    private String username;

    public Username(){}

    public Username(String username){
        this.username = username;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
