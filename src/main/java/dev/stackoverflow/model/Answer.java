package dev.stackoverflow.model;


import org.springframework.data.annotation.Id;

import java.util.Date;


public class Answer {
    @Id
    private Long id;
    private String text;
    private User author;
    private Date creationDate;
    private int voteNumber;
}
