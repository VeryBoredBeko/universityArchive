package com.satbayevUniversity.universityArchive.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "Shelves")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShelfID", nullable = false, updatable = false)
    private Long shelfId;

    @Column(name = "ShelfNumber", nullable = false, length = 50)
    private String shelfNumber;

    @Column(name = "Description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shelf")
    private List<StudentRecord> studentRecords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shelf")
    private List<EmployeeRecord> employeeRecords;

    public Shelf(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    @Override
    public String toString() {
        return "ShelfNumber: #" + shelfNumber + ", description: " + description;
    }
}
