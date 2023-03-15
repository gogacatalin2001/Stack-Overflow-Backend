package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "tag_id", nullable = false, unique = true)
    private  Long tagId;
    @NonNull
    @Column(name = "text",columnDefinition = "varchar(50) default '#tag'", nullable = false, unique = true)
    private String text;

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
