package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentCommentForm {

    @NotNull
    private StudentRecord studentRecord;

    @NotNull
    private User user;

    private String commentText;

    public StudentComment toStudentComment() {
        return new StudentComment(studentRecord, user, commentText);
    }
}
