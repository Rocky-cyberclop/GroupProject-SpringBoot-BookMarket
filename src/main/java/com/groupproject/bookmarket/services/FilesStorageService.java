package com.groupproject.bookmarket.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FilesStorageService {
    public void saveBookImages(List<MultipartFile> files);
    public Resource loadFileWithPath(Path rootFilePath, String fileName);
    public void deleteBookImages(List<String> fileNames);
}
