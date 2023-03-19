package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class Answer extends Post {
}
