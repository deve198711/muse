package com.musebay.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<String> mediaFiles;
    private String name;
    private String email;
    private LocalDate birthdate;

    private String profileImagePath;
    private String idImagePath;

    @Column(length = 1000)
    private String about;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Submission(String name, String email, LocalDate birthdate,
                      String profileImagePath, String idImagePath,
                      List<String> mediaFiles, String about,
                      LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.profileImagePath = profileImagePath;
        this.idImagePath = idImagePath;
        this.mediaFiles = mediaFiles;
        this.about = about;
        this.createdAt = createdAt;
    }

}
