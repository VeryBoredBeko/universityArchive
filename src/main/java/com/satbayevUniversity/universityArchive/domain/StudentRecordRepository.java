package com.satbayevUniversity.universityArchive.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRecordRepository extends JpaRepository<StudentRecord, Long> {

    @Query("SELECT sr FROM StudentRecord sr WHERE " +
            "(LOWER(sr.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.country) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.faculty) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.department) LIKE LOWER(CONCAT('%', :searchText, '%'))) AND " +
            "sr.isResident = :isResident")
    Page<StudentRecord> findBySearchTextAndIsResident(@Param("searchText") String searchText,
                                                      @Param("isResident") Boolean isResident,
                                                      Pageable pageable);

    @Query("SELECT sr FROM StudentRecord sr WHERE " +
            "LOWER(sr.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.country) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.faculty) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(sr.department) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<StudentRecord> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

    Page<StudentRecord> findByIsResident(Boolean isResident, Pageable pageable);
}
