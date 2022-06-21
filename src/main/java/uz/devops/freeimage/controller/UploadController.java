package uz.devops.freeimage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.devops.freeimage.config.UploadProperties;
import uz.devops.freeimage.payload.ApiResponse;
import uz.devops.freeimage.payload.ResponseDto;
import uz.devops.freeimage.service.UploadService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UploadProperties uploadProperties;

    @PostMapping("/fromMultipartFile")
    public List<ResponseDto> upload(@RequestPart MultipartFile file) throws IOException {
        return uploadService.uploadPhoto(file);
    }

    @PostMapping("/fromFolder")
    public List<ApiResponse> uploadPhoto() throws IOException {
        return uploadService.uploadPhoto(uploadProperties.getImageFolderUrl());
    }
}
