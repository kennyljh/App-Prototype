package com.careerconnect.app.CareerClusters;

import java.util.Set;

public class CareerClusterDTO {

    private Set<CareerCluster> selectedCareerClusters;

    public Set<CareerCluster> getSelectedCareerClusters() {
        return selectedCareerClusters;
    }

    public void setSelectedCareerClusters(Set<CareerCluster> selectedCareerClusters) {
        this.selectedCareerClusters = selectedCareerClusters;
    }
}
