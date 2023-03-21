package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(unique = true)
    private String text;

    public Tag() {
    }

    public Tag(String text) {
        this.text = text;
    }

    public Tag(Long id, String text) {
        this.id = id;
        this.text = text;
    }

}
