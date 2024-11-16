package com.satbayevUniversity.universityArchive.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "StudentComments")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class StudentComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "RecordID", nullable = false)
    private StudentRecord studentRecord;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "CommentText", nullable = false)
    private String commentText;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public StudentComment(StudentRecord studentRecord, User user, String commentText) {
        this.studentRecord = studentRecord;
        this.user = user;
        this.commentText = commentText;
    }
}
