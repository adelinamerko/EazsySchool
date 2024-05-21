package com.eazybytes.eazyschool.utils;

import com.eazybytes.eazyschool.model.Courses;
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
    private static final String USER_PROFILE_IMAGE_DIR = "./src/main/resources/static/assets/images/user-profiles/";
    private static final String COURSE_IMAGE_DIR = "./src/main/resources/static/assets/images/course-images/";

    public static String uploadProfileImage(MultipartFile file, Person person) {
        return uploadImage(file, person, USER_PROFILE_IMAGE_DIR, "profile image");
    }

    public static String uploadCourseImage(MultipartFile file, Courses course) {
        return uploadImage(file, course, COURSE_IMAGE_DIR, "course image");
    }

    private static String uploadImage(MultipartFile file, Object entity, String uploadDir, String entityType) {
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = createDirectoryAndResolveFilePath(uploadDir, uniqueFileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            deleteExistingImage(entity);
            return getRelativeImagePath(uploadDir, uniqueFileName);
        } catch (IOException e) {
            log.error("Failed to upload {} for {}: {}", entityType, entity, e.getMessage());
            return "";
        }
    }

    private static Path createDirectoryAndResolveFilePath(String uploadDir, String uniqueFileName) {
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                log.error("Failed to create directory for {}: {}", uploadDir, e.getMessage());
                return null;
            }
        }
        return dirPath.resolve(uniqueFileName);
    }

    private static void deleteExistingImage(Object entity) throws IOException {
        if (entity instanceof Person) {
            Person person = (Person) entity;
            if (person.getProfileImagePath()!= null &&!person.getProfileImagePath().isEmpty()) {
                Path previousImagePath = Paths.get("./src/main/resources/static/" + person.getProfileImagePath());
                Files.deleteIfExists(previousImagePath);
            }
        } else if (entity instanceof Courses) {
            Courses course = (Courses) entity;
            if (course.getCourseImagePath()!= null &&!course.getCourseImagePath().isEmpty()) {
                Path previousImagePath = Paths.get("./src/main/resources/static/" + course.getCourseImagePath());
                Files.deleteIfExists(previousImagePath);
            }
        }
    }

    private static String getRelativeImagePath(String uploadDir, String uniqueFileName) {
        return uploadDir.replace("./src/main/resources/static", "") + uniqueFileName;
    }

    private static String generateUniqueFileName(String originalFileName) {
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        return fileNameWithoutExtension + "_" + UUID.randomUUID() + "." + fileExtension;
    }

}
