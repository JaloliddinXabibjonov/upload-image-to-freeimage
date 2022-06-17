package UploadPhoto.upload.service.impl;

import UploadPhoto.upload.payload.FilePath;
import UploadPhoto.upload.payload.ResponseDto;
import UploadPhoto.upload.service.UploadService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import UploadPhoto.upload.config.TestConfig;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
public class UploadServiceImplTest {

    @Autowired
    private UploadService uploadService;


    @Test
    public void test() throws IOException {
        assertNotEquals("OK", uploadService.uploadPhoto(new FilePath("D:\\projects\\Upload\\images")));
    }
}
