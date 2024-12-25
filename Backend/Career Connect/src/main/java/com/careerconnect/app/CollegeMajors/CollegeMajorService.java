package com.careerconnect.app.CollegeMajors;

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
public class CollegeMajorService {

    @Autowired
    private CollegeMajorRepository collegeMajorRepository;

    @Transactional
    public void loadCollegeMajorsFromCsv(){

        try {
            ClassPathResource resource = new ClassPathResource("majorsList.csv");
            CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()));

            List<String[]> rows = csvReader.readAll();
            List<CollegeMajor> collegeMajorList = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++){

                String[] row = rows.get(i);
                CollegeMajor collegeMajor = new CollegeMajor();
                collegeMajor.setId(Long.parseLong(row[0]));
                collegeMajor.setName(row[3]);
                collegeMajor.setCategory(row[2]);

                collegeMajorList.add(collegeMajor);
            }
            collegeMajorRepository.saveAll(collegeMajorList);
        } catch (IOException | CsvException e){
            throw new RuntimeException(e);
        }
    }
}
