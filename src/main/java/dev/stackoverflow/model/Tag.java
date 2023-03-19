package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tag")
public class Tag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private  Long tagId;
    @Column
    private String text;


    public Tag() {
    }

    public Tag(String text) {
        this.text = text;
    }

    public Tag(Long tagId, String text) {
        this.tagId = tagId;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getTagId().equals(tag.getTagId()) && getText().equals(tag.getText());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
