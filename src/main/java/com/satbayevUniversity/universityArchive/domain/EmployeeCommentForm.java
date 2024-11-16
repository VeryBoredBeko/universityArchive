package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeCommentForm {

    @NotNull
    private EmployeeRecord employeeRecord;

    @NotNull
    private User user;

    private String commentText;

    public EmployeeComment toEmployeeComment() {
        return new EmployeeComment(employeeRecord, user, commentText);
    }
}
