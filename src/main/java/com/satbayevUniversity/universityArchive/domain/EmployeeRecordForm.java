package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRecordForm {

    @NotBlank(message = "Поле для ИИН не может быть пустым")
    @Size(min = 12, max = 12, message = "ИИН должен состоять из 12 цифр")
    private String iin;

    @NotBlank(message = "Поле для имени не может быть пустым")
    @Size(min = 2, max = 50, message = "Длина имени от 2 до 50")
    private String firstName;

    @NotBlank(message = "Поле для фамилии не может быть пустым")
    @Size(min = 2, max = 50, message = "Длина фамилии от 2 до 50")
    private String lastName;

    private String country;

    private String region;

    private Boolean isResident;

    private Integer yearOfHire;

    private Integer yearOfTermination;

    @Size(max = 100)
    private String faculty;

    @Size(max = 100)
    private String department;

    // TODO: Надо проверить номер на уникальность.
    @NotBlank(message = "Поле для номера записи не может быть пустым")
    private String recordNumber;

    private Shelf shelf;

    public EmployeeRecord toEmployeeRecord() {
        EmployeeRecord employeeRecord = new EmployeeRecord(
                iin, firstName, lastName, recordNumber
        );

        employeeRecord.setCountry(country);
        employeeRecord.setRegion(region);
        employeeRecord.setIsResident(isResident);
        employeeRecord.setYearOfHire(yearOfHire);
        employeeRecord.setYearOfTermination(yearOfTermination);
        employeeRecord.setFaculty(faculty);
        employeeRecord.setDepartment(department);
        employeeRecord.setShelf(shelf);

        return employeeRecord;
    }

    public EmployeeRecordForm() {}

    public EmployeeRecordForm(EmployeeRecord record) {
        this.iin = record.getIin();
        this.firstName = record.getFirstName();
        this.lastName = record.getLastName();
        this.country = record.getCountry();
        this.region = record.getRegion();
        this.isResident = record.getIsResident();
        this.yearOfHire = record.getYearOfHire();
        this.yearOfTermination = record.getYearOfTermination();
        this.faculty = record.getFaculty();
        this.department = record.getDepartment();
        this.recordNumber = record.getRecordNumber();
        this.shelf = record.getShelf();
    }
}
