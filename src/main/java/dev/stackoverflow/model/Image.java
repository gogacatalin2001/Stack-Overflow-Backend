package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Lob
    @Column(nullable = false)
    private byte[] imageData;
}
