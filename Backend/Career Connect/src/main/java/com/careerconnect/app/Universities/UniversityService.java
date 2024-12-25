package com.careerconnect.app.Universities;

import com.careerconnect.app.CollegeMajors.CollegeMajor;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService {

    @Autowired
    UniversityRepository universityRepository;

    @Transactional
    public void loadUniversitiesFromCsv(){

        try {
            ClassPathResource resource = new ClassPathResource("world_universities_and_domains.csv");
            CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()));

            List<String[]> rows = csvReader.readAll();
            List<University> universityList = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++){

                String[] row = rows.get(i);
                University university = new University();
                university.setName(row[0]);
                university.setDomain(row[1]);
                university.setWebpage(row[2]);
                university.setCountry(row[3]);
                university.setCountryCode(row[4]);

                universityList.add(university);
            }
            universityRepository.saveAll(universityList);
        } catch (IOException | CsvException e){
            throw new RuntimeException(e);
        }
    }
}
