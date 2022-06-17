package UploadPhoto.upload.service.impl;

import UploadPhoto.upload.config.TestConfig;
import UploadPhoto.upload.payload.ApiResponse;
import UploadPhoto.upload.payload.FilePath;
import UploadPhoto.upload.service.UploadService;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
public class UploadServiceTest {
    @Autowired
    private UploadService uploadService;

    private static final String filePath = "D:\\projects\\Upload\\images";

    @Test
    public void fileTypeTest() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(new FilePath("D:\\OtherFiles"));
        Assertions.assertThat(apiResponseList.size()).isEqualTo(2);
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isNotNull();
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto()).isNull();
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isEqualTo("This file type isn't image");
    }

    @Test
    public void filesTest() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(new FilePath(filePath));
        Assertions.assertThat(apiResponseList.size()).isEqualTo(3);
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isEqualTo("Image saved");
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getImage().getUrl_viewer()).isNotNull();
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getStatus_code()).isEqualTo(200);
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getStatus_txt()).isEqualTo("OK");
    }
    @Test
    public void emptyFolderTest() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(new FilePath("D:\\Empty"));
        Assertions.assertThat(apiResponseList.size()).isEqualTo(1);
        Assertions.assertThat(apiResponseList.get(0).getResponseDto()).isNull();
        Assertions.assertThat(apiResponseList.get(0).getMessage()).isEqualTo("Folder is empty");
    }

    @Test
    public void hasFolderTest() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(new FilePath("filePath"));
        Assertions.assertThat(apiResponseList.get(0).getMessage()).isEqualTo("Folder not found");
        Assertions.assertThat(apiResponseList.get(0).getResponseDto()).isNull();
    }


}
