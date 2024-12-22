package com.careerconnect.app.CareerClusters;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CareerClusterDTO {

    @NotNull(message = "Selected career clusters cannot be null")
    @Size(min = 1, message = "At least one career cluster must be selected")
    private Set<CareerCluster> selectedCareerClusters;

    public Set<CareerCluster> getSelectedCareerClusters() {
        return selectedCareerClusters;
    }

    public void setSelectedCareerClusters(Set<CareerCluster> selectedCareerClusters) {
        this.selectedCareerClusters = selectedCareerClusters;
    }
}
