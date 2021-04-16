package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.repositories.contracts.ImageRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final Path root = Paths.get(".").normalize().toAbsolutePath();
    private final String RESOURCES_DIR = root.toString() + "/src/main/resources/static/images/";
    private final String OUTPUT_DIR = "/images/";



    @Override
    public String saveImage(MultipartFile image, String imageName) {
        String filename = new Date().getTime() + "_" + imageName;
        Path path = Paths.get(RESOURCES_DIR + filename);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }

        return OUTPUT_DIR + filename;

    }

    @Override
    public FileSystemResource getImage(String path) {
        try {
            return new FileSystemResource(Paths.get(RESOURCES_DIR + path));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
