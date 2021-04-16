package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.repositories.contracts.ImageRepository;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTests {

    @Mock
    ImageRepository imageRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    public void saveImageShould_Call_Repository_WhenValid(){
        imageService.saveImage(any(), anyString());

        verify(imageRepository,timeout(1)).saveImage(any(),anyString());
    }

    @Test
    public void getImageShould_Call_Repository_WhenValid(){
        imageService.getImage(anyString());

        verify(imageRepository,timeout(1)).getImage(anyString());
    }
}
