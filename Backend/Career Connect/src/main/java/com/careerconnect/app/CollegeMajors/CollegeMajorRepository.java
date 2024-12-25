package com.careerconnect.app.CollegeMajors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollegeMajorRepository extends JpaRepository<CollegeMajor, Long> {

    List<CollegeMajor> findByNameContaining(String name, Sort sort);
}
