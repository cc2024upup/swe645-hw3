package edu.gmu.springboot.survey.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;
    private String url;

    private LocalDate date;

    @ElementCollection
    private List<String> likes;

    private String source;
    private String recommend;
    private String comments;
}

