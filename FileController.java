package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/download/{filename:.+}")

    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        logger.info("Request received to download file: {}", filename);

        // Create the file object          //file directory                 //file name
        File file = new File("C:\\Users\\sainilakshay\\Documents\\cor_hierarchy.txt");

        // Check if the file exists
        if (!file.exists()) {
            logger.warn("File not found: {}", file.getAbsolutePath());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            // Use InputStreamResource to stream the file and high memory files
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}