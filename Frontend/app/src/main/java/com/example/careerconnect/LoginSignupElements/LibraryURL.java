package com.example.careerconnect.LoginSignupElements;

/**
 * Stores URLs pertaining to the LoginSignupElements package
 */
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

    public static String getCareerClustersPOSTRequest(){
        return "http://10.0.2.2:8080/careerCluster/post/";
    }
}
