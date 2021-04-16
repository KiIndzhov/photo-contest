package com.telerikfinalproject.photocontest.services.contracts;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {


    String saveImage(MultipartFile coverPhoto, String title);

    FileSystemResource getImage(String fileName);

}
