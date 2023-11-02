package io.shop.services.impl;

import io.shop.repository.StoredImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private StoredImageRepository storedImageRepository;

    @InjectMocks
    private ImageServiceImpl out;

    @Test
    void updateImage() {
    }
}