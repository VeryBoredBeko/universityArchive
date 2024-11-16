package com.satbayevUniversity.universityArchive.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {

    @Query("SELECT sr FROM EmployeeRecord sr " +
            "WHERE LOWER(sr.iin) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.firstName) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.lastName) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.country) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.region) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.faculty) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR LOWER(sr.department) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "OR str(sr.yearOfHire) LIKE CONCAT(:searchText, '%') " +
            "OR str(sr.yearOfTermination) LIKE CONCAT(:searchText, '%')")
    Page<EmployeeRecord> findRecordBySearchText(@Param("searchText") String searchText, Pageable pageable);
}
