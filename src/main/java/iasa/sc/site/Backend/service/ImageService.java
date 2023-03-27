package iasa.sc.site.Backend.service;

import iasa.sc.site.Backend.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    List<Image> getAllImagesByUUID(UUID uuid);

    Image getImageByUUID(UUID uuid);

    void saveAllImages(List<MultipartFile> images, UUID uuid);

    void saveImage(MultipartFile image, UUID uuid);

    void deleteAllImagesByUUID(UUID uuid);
}
