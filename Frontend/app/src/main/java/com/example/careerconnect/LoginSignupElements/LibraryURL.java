package com.example.careerconnect.LoginSignupElements;

public class LibraryURL {

    public static String getUsernameCheckGETRequest(){
        return "http://10.0.2.2:8080/username/check/";
    }

    public static String getAccountCreationPOSTRequest(){
        return "http://10.0.2.2:8080/account/create";
    }

    public static String getCareerClustersGETRequest(){
        return "http://10.0.2.2:8080/careerCluster/get";
    }
}
