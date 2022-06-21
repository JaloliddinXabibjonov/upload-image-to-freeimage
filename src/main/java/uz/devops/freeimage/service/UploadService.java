package uz.devops.freeimage.service;

import uz.devops.freeimage.config.UploadProperties;
import uz.devops.freeimage.payload.ApiResponse;
import uz.devops.freeimage.payload.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {
    @Autowired
    RestTemplate restTemplate;
    HttpHeaders headers=new HttpHeaders();
   private final UploadProperties uploadProperties;

    public List<ResponseDto> uploadPhoto(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        return Collections.singletonList(uploadOneImage(bytes));
    }


    public List<ApiResponse> uploadPhoto(String uploadFolderUrl) throws IOException {
        List<ApiResponse> apiResponseList = new ArrayList<>();
        if (uploadFolderUrl != null && new File(uploadFolderUrl).exists() && new File(uploadFolderUrl).isDirectory()) {
            if (new File(uploadFolderUrl).listFiles().length != 0) {
                File[] listFiles = new File(uploadFolderUrl).listFiles();
                for (File file : listFiles) {
                    if (file.isFile() && Files.probeContentType(file.toPath()).startsWith("image")) {
                        byte[] bytes = Files.readAllBytes(file.toPath());
                        ResponseDto responseDto = uploadOneImage(bytes);
                        apiResponseList.add(new ApiResponse("Image saved", responseDto));
                    } else {
                        apiResponseList.add(new ApiResponse("This file type isn't image", null));
                    }
                }
            } else {
                apiResponseList.add(new ApiResponse("Folder is empty", null));
            }
        } else {
            apiResponseList.add(new ApiResponse("Folder not found", null));
        }
        return apiResponseList;
    }


    public ResponseDto uploadOneImage(byte[] bytes) {
        String s = Base64.getEncoder().encodeToString(bytes);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("source", s);
        body.add("type", "file");
        body.add("action", "upload");
        body.add("key", uploadProperties.getImageKey());
        headers.setContentType(new MediaType("multipart", "form-data"));
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(uploadProperties.getUrl(), HttpMethod.POST, requestEntity, ResponseDto.class);
        return response.getBody();
    }
}