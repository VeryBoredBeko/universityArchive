package com.satbayevUniversity.universityArchive.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EmployeeComments")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EmployeeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "RecordID", nullable = false)
    private EmployeeRecord employeeRecord;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "CommentText", nullable = false)
    private String commentText;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public EmployeeComment(EmployeeRecord employeeRecord, User user, String commentText) {
        this.employeeRecord = employeeRecord;
        this.user = user;
        this.commentText = commentText;
    }
}
