package com.app.curioq.qaservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String questionDescription;
    @Column(name = "useremail")
    private String userEmail;
    @Column(name = "userid")
    private long userId;

    private Set<Long> likedBy;

    @JsonIgnoreProperties("question")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

}
