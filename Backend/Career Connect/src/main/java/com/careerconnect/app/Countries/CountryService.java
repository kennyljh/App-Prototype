package com.careerconnect.app.Countries;

import com.careerconnect.app.Universities.University;
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
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Transactional
    public void loadCountriesFromCsv(){

        if (!countryRepository.findAll().isEmpty()){
            return;
        }

        try {
            ClassPathResource resource = new ClassPathResource("country.csv");
            CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()));

            List<String[]> rows = csvReader.readAll();
            List<Country> countryList = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++){

                String[] row = rows.get(i);
                Country country = new Country();
                country.setId(Long.parseLong(String.valueOf(i)));
                country.setName(row[1]);
                country.setCode(row[0]);

                countryList.add(country);
            }
            countryRepository.saveAll(countryList);
        } catch (IOException | CsvException e){
            throw new RuntimeException(e);
        }
    }
}
