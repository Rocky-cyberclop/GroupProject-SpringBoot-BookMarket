package com.groupproject.bookmarket.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilesStorageService {
    public void saveBookImages(List<MultipartFile> files);
    public void deleteBookImages(List<String> fileNames);
}
