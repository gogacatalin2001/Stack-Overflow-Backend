package dev.stackoverflow.controller;

import dev.stackoverflow.model.Image;
import dev.stackoverflow.service.ImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "*", allowCredentials = "true")
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @PostMapping( "/images")
    @ResponseBody
    public Long saveImage(
            @RequestParam("image") MultipartFile image) throws IOException {
        return imageService.save(image).getId();
    }

    @GetMapping("/images/image-data")
    public ResponseEntity<?> getImageData(@RequestParam("image_id") @NonNull Long imageId
    ) {
        Image image = imageService.getById(imageId);
        if (image != null) {
        return ResponseEntity
                .status(200)
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getImageData());
        }
        return ResponseEntity.status(404).body("Image not found");
    }

    @DeleteMapping("/images")
    public void deleteImage(
            @RequestParam("image_id") @NonNull Long imageId
    ) {
        imageService.delete(imageId);
    }
}
