package com.eazybytes.eazyschool.utils;

import com.eazybytes.eazyschool.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
public class Utilities {
    public static String uploadProfileImage(MultipartFile file, Person person) {
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path uploadDir = Paths.get("./src/main/resources/static/assets/images/user-profiles/");
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (IOException e) {
                log.error("Failed to create directory for profile images: {}", e.getMessage());
                return null;
            }
        }
        Path filePath = uploadDir.resolve(uniqueFileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            if (person.getProfileImagePath() != null && !person.getProfileImagePath().isEmpty()) {
                Path previousImagePath = Paths.get("./src/main/resources/static/" + person.getProfileImagePath());
                Files.deleteIfExists(previousImagePath);
            }
            return "assets/images/user-profiles/" + uniqueFileName;
        } catch (IOException e) {
            log.error("Failed to upload profile image: {}", e.getMessage());
            return null;
        }
    }

    private static String generateUniqueFileName(String originalFileName) {
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        return fileNameWithoutExtension + "_" + UUID.randomUUID() + "." + fileExtension;
    }
}
