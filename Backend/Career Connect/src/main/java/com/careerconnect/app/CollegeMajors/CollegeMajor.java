package com.careerconnect.app.CollegeMajors;

import jakarta.persistence.*;

@Entity
@Table(name = "collegeMajors")
public class CollegeMajor {

    @Id
    private Long id;
    private String name;
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
