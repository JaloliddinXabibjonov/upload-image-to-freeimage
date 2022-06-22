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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {
    @Autowired
    RestTemplate restTemplate;
    HttpHeaders headers=new HttpHeaders();
   private final UploadProperties uploadProperties;

    public ApiResponse uploadPhoto(MultipartFile multipartFile) throws IOException {
        ApiResponse apiResponse=new ApiResponse();
        if (multipartFile!=null&&multipartFile.getContentType().startsWith("image")) {
            byte[] bytes = multipartFile.getBytes();
            apiResponse.setMessage("Image saved");
            apiResponse.setResponseDto(uploadOneImage(bytes));
            return apiResponse;
        }
        apiResponse.setMessage("Sending file type isn't image");
        return apiResponse;
    }


    public List<ApiResponse> uploadPhoto(String uploadFolderUrl) throws IOException {
        List<ApiResponse> apiResponseList = new ArrayList<>();
        ApiResponse apiResponse=new ApiResponse();
        File file1 = new File(uploadFolderUrl);
        if (uploadFolderUrl != null && file1.exists() && file1.isDirectory()) {
            if (file1.listFiles().length != 0) {
                File[] listFiles = file1.listFiles();
                for (File file : listFiles) {
                    if (file.isFile() && Files.probeContentType(file.toPath()).startsWith("image")) {
                        byte[] bytes = Files.readAllBytes(file.toPath());
                        ResponseDto responseDto = uploadOneImage(bytes);
                        apiResponse.setMessage("Image saved");
                        apiResponse.setResponseDto(responseDto);
                        apiResponseList.add(apiResponse);
                    } else {
                        apiResponse.setMessage("This file type isn't image");
                        apiResponseList.add(apiResponse);
                    }
                }
            } else {
                apiResponse.setMessage("Folder is empty");
                apiResponseList.add(apiResponse);
            }
        } else {
            apiResponse.setMessage("Folder not found");
            apiResponseList.add(apiResponse);
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
