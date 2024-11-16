package com.satbayevUniversity.universityArchive.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagingStudentRecordRepository extends PagingAndSortingRepository<StudentRecord, Long> {

    @Query("SELECT sr FROM StudentRecord sr " +
            "WHERE LOWER(sr.iin) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.firstName) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.lastName) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.country) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.region) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.faculty) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.department) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR str(sr.yearOfEnrollment) LIKE CONCAT(:searchText, '%') " +
            "OR str(sr.yearOfGraduation) LIKE CONCAT(:searchText, '%')")
    List<StudentRecord> findRecordBySearchText(@Param("searchText") String searchText);
}
