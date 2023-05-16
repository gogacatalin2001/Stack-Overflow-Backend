package dev.stackoverflow.service;

import dev.stackoverflow.exception.ImageNotFoundException;
import dev.stackoverflow.model.Image;
import dev.stackoverflow.repository.ImageRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image save(@NonNull MultipartFile file) throws IOException {
        Image existingImage = imageRepository.getByName(file.getOriginalFilename());
        if (existingImage == null) {
            Image image = Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .build();
            return imageRepository.save(image);
        } else {
            byte[] imageData = ImageUtil.decompressImage(existingImage.getImageData());
            existingImage.setImageData(imageData);
            return existingImage;
        }
    }

    public Image getById(@NonNull Long id) {
        Optional<Image> image = imageRepository.findById(id);
        Image decompressedImage = new Image();
        image.ifPresent(img -> {
            byte[] imageData = ImageUtil.decompressImage(image.get().getImageData());
            decompressedImage.setId(id);
            decompressedImage.setName(img.getName());
            decompressedImage.setType(img.getType());
            decompressedImage.setImageData(imageData);
        });
        return decompressedImage;
    }

    public void delete(Image image) {
        imageRepository.delete(image);
    }

    public void delete(@NonNull Long id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
        } else {
            throw new ImageNotFoundException(id);
        }
    }

    private static class ImageUtil {
        public static byte[] compressImage(byte[] data) {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            try {
                outputStream.close();
            } catch (Exception ignored) {
            }
            return outputStream.toByteArray();
        }

        public static byte[] decompressImage(byte[] data) {
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4 * 1024];
            try {
                while (!inflater.finished()) {
                    int count = inflater.inflate(tmp);
                    outputStream.write(tmp, 0, count);
                }
                outputStream.close();
            } catch (Exception ignored) {
            }
            return outputStream.toByteArray();
        }
    }
}
