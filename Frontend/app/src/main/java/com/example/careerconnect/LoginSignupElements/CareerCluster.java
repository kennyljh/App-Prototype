package com.example.careerconnect.LoginSignupElements;

import android.widget.ImageView;

/**
 * Defines the elements of a Career Cluster
 */
public class CareerCluster {

    private int id;
    private String name;
    private int iconResource;

    public CareerCluster(){}

    public CareerCluster(int id, String name, int iconResource){
        this.id = id;
        this.name = name;
        this.iconResource = iconResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
