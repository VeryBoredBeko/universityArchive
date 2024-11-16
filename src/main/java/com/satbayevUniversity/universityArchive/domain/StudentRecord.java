package com.satbayevUniversity.universityArchive.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "StudentRecords")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class StudentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordId")
    private Long recordId;

    // TODO: В базу данных добавить unique constraint
    @Column(name = "IIN", length = 12, nullable = false, unique = true)
    private String iin;

    @Column(name = "FirstName", length = 50)
    private String firstName;

    @Column(name = "LastName", length = 50)
    private String lastName;

    @Column(name = "Country", length = 100)
    private String country;

    @Column(name = "Region", length = 100)
    private String region;

    @Column(name = "IsResident")
    private Boolean isResident;

    @Column(name = "YearOfEnrollment")
    private Integer yearOfEnrollment;

    @Column(name = "YearOfGraduation")
    private Integer yearOfGraduation;

    @Column(name = "Faculty", length = 100)
    private String faculty;

    @Column(name = "Department", length = 100)
    private String department;

    @Column(name = "RecordNumber", length = 50, unique = true)
    private String recordNumber;

    @ManyToOne
    @JoinColumn(name = "ShelfID")
    private Shelf shelf;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    // TODO: Добавить StudentComments

    public StudentRecord(String iin, String firstName, String lastName, String recordNumber) {
        this.iin = iin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.recordNumber = recordNumber;
    }
}
