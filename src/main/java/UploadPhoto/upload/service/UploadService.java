package UploadPhoto.upload.service;

import UploadPhoto.upload.ApiConstants;
import UploadPhoto.upload.payload.ApiResponse;
import UploadPhoto.upload.payload.FilePath;
import UploadPhoto.upload.payload.ResponseDto;
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

@Service
public class UploadService {

    public List<ResponseDto> uploadPhoto(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        return Collections.singletonList(uploadOneImage(bytes));
    }


    public List<ApiResponse> uploadPhoto(FilePath filePath) throws IOException {
        List<ApiResponse> apiResponseList = new ArrayList<>();
        if (filePath.getPath() != null && new File(filePath.getPath()).exists() && new File(filePath.getPath()).isDirectory()) {
            if (new File(filePath.getPath()).listFiles().length!=0) {
                File[] listFiles = new File(filePath.getPath()).listFiles();
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
        body.add("key", "6d207e02198a847aa98d0a2a901485a5");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("multipart", "form-data"));
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDto> response = restTemplate.exchange(ApiConstants.FREE_IMAGE, HttpMethod.POST, requestEntity, ResponseDto.class);
        return response.getBody();
    }
}
