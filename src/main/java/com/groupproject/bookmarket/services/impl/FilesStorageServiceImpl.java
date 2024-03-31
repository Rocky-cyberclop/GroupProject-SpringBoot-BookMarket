package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.services.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

//Reference from https://bezkoder.com/
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("src/main/resources/static/uploads/");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public Resource loadFileWithPath(Path rootFilePath, String fileName) {
        try {
            Path file = rootFilePath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void saveBookImages(List<MultipartFile> files) {
        Path path = Paths.get("src/main/resources/static/uploads/images/book");
        files.forEach(file -> {
            try {
                Files.copy(file.getInputStream(), path.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("This file (" + file.getOriginalFilename() + ") already exists.");
                }
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public void deleteBookImages(List<String> fileNames) {
        Path finalPath = Paths.get("src/main/resources/static/uploads/images/book");

        fileNames.forEach(fileName -> {
            try {
                Path fileDelete = finalPath.resolve(Objects.requireNonNull(fileName));
                Files.deleteIfExists(fileDelete);
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("Cannot delete this file: (" + fileName + ")");
                }

                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
