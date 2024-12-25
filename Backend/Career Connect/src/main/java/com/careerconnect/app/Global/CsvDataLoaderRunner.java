package com.careerconnect.app.Global;

import com.careerconnect.app.CollegeMajors.CollegeMajorService;
import com.careerconnect.app.Universities.UniversityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvDataLoaderRunner implements CommandLineRunner {

    private final CollegeMajorService collegeMajorService;
    private final UniversityService universityService;

    public CsvDataLoaderRunner(CollegeMajorService collegeMajorService, UniversityService universityService){
        this.collegeMajorService = collegeMajorService;
        this.universityService = universityService;
    }

    @Override
    public void run(String... args) throws Exception {

        collegeMajorService.loadCollegeMajorsFromCsv();
        universityService.loadUniversitiesFromCsv();
    }
}
