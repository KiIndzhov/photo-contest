package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.repositories.contracts.ImageRepository;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String saveImage(MultipartFile image, String imageName) {
        return imageRepository.saveImage(image,imageName);
    }
    @Override
    public FileSystemResource getImage(String path) {
        return imageRepository.getImage(path);
    }

    @Override
    public String getImagePath(String fileName) {
        return imageRepository.getImagePath(fileName);
    }
}
