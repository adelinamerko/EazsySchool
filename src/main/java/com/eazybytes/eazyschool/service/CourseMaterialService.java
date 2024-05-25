package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.CourseMaterial;
import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.repository.CourseMaterialRepository;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Service
public class CourseMaterialService {
    private final int SIZE_10_MB = 10 * 1024 * 1024 * 9999999;
    private final String MATERIALS_DIRECTORY = "./src/main/resources/static/";

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    public void addCourseMaterial(int courseId, MultipartFile file) throws IOException {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        // Validate if the course already has 8 materials
        if (course.getCourseMaterials().size() >= 8) {
            throw new IllegalArgumentException("Maximum number of course materials reached for this course");
        }

        // Check file type
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        // Validate file size
        if (file.getSize() > SIZE_10_MB) {
            throw new IllegalArgumentException("File size exceeds 10MB limit");
        }

        // replace characters in the course name that are not alphanumeric (letters or numbers) or hyphens or underscores with underscores.
        String sanitizedCourseName = course.getName().replaceAll("[^a-zA-Z0-9-_]", "_");

        // Create directory for course materials if not exist
        Path courseDir = Paths.get("./src/main/resources/static/course-materials/" + sanitizedCourseName);
        log.error("Course directory: " + courseDir.toString());

        if (!Files.exists(courseDir)) {
            Files.createDirectories(courseDir);
        }

        // Generate a unique filename to avoid duplicates
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = getUniqueFileName(originalFileName, courseDir);

        // Save file to filesystem
        Path filePath = courseDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Create CourseMaterial entity and save it
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setName(fileName);
        courseMaterial.setFilePath("course-materials/" + sanitizedCourseName + "/" + fileName);
        courseMaterial.setCourse(course);

        courseMaterialRepository.save(courseMaterial);
    }

    /**
     * This method generates a unique filename by appending a counter to the original filename
     * if a file with the same name already exists in the course directory.
     *
     * @param originalFileName
     * @param courseDir
     * @return unique filename
     */
    private String getUniqueFileName(String originalFileName, Path courseDir) {
        String fileName = originalFileName;
        int counter = 1;
        while (Files.exists(courseDir.resolve(fileName))) {
            String name = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            fileName = name + "_" + counter + extension;
            counter++;
        }
        return fileName;
    }

    @Transactional
    public void deleteMaterial(int materialId) throws IOException {
        CourseMaterial material = courseMaterialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        // Delete file from filesystem
        Path filePath = Paths.get("./src/main/resources/static/" + material.getFilePath());
        Files.deleteIfExists(filePath);

        // Delete material entity from database
        courseMaterialRepository.delete(material);
    }

    public Resource downloadMaterial(int materialId) {
        CourseMaterial material = courseMaterialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        String filePath = MATERIALS_DIRECTORY + material.getFilePath();
        Path path = Paths.get(filePath);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Material file not found or cannot be read");
        }


        return resource;
    }
}
