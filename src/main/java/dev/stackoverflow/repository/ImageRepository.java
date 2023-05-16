package dev.stackoverflow.repository;

import dev.stackoverflow.model.Image;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ImageRepository extends JpaRepositoryImplementation<Image, Long> {
//    Image save(Image image);
    Image getByName(String imageName);
    Image getById(Long id);
    void deleteById(Long id);
}
