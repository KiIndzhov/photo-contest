package com.telerikfinalproject.photocontest.repositories.contracts;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {

    String saveImage(MultipartFile multipartFile, String imageName);

    FileSystemResource getImage(String path);

}
