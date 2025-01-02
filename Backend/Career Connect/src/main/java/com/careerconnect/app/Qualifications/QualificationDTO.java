package com.careerconnect.app.Qualifications;

import jakarta.validation.constraints.NotNull;

public class QualificationDTO {

    private Long id;

    @NotNull(message = "Qualification title cannot be null")
    private String title;

    @NotNull(message = "Qualification description cannot be null")
    private String description;

    @NotNull(message = "Qualification duration cannot be null")
    private String duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
