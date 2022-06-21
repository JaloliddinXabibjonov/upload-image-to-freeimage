package uz.devops.freeimage.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uz.devops.freeimage.TestConfig;
import uz.devops.freeimage.config.UploadProperties;
import uz.devops.freeimage.payload.ApiResponse;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
public class UploadServiceTest {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private UploadProperties uploadProperties;

    @Test
    public void testUploadFolderWithOtherFiles() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(uploadProperties.getOtherFilesFolderUrl());
        Assertions.assertThat(apiResponseList.size()).isEqualTo(2);
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isNotNull();
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto()).isNull();
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isEqualTo("This file type isn't image");
    }

    @Test
    public void testUploadFolderWithImages() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(uploadProperties.getImageFolderUrl());
        Assertions.assertThat(apiResponseList.size()).isEqualTo(3);
        Assertions.assertThat(apiResponseList.iterator().next().getMessage()).isEqualTo("Image saved");
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getImage().getUrl_viewer()).isNotNull();
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getStatus_code()).isEqualTo(200);
        Assertions.assertThat(apiResponseList.iterator().next().getResponseDto().getStatus_txt()).isEqualTo("OK");
    }

    @Test
    public void testUploadEmptyFolder() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto(uploadProperties.getEmptyFolderUrl());
        Assertions.assertThat(apiResponseList.size()).isEqualTo(1);
        Assertions.assertThat(apiResponseList.get(0).getResponseDto()).isNull();
        Assertions.assertThat(apiResponseList.get(0).getMessage()).isEqualTo("Folder is empty");
    }

    @Test
    public void testUploadNotExistingFolder() throws IOException {
        List<ApiResponse> apiResponseList = uploadService.uploadPhoto("  ");
        Assertions.assertThat(apiResponseList.get(0).getMessage()).isEqualTo("Folder not found");
        Assertions.assertThat(apiResponseList.get(0).getResponseDto()).isNull();
    }
}
