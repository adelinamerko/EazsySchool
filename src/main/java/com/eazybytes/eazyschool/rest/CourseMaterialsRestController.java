package com.eazybytes.eazyschool.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.eazyschool.service.CourseMaterialService;

@RestController
@RequestMapping("/courseMaterials")
public class CourseMaterialsRestController {
    private final CourseMaterialService courseMaterialService;

    public CourseMaterialsRestController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping(value = "/downloadMaterial", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadMaterial(@RequestParam("materialId") int materialId) {
        // Get the file resource from the service
        Resource resource = courseMaterialService.downloadMaterial(materialId);

        // Set content type and disposition headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", resource.getFilename());

        // Return the file resource as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}