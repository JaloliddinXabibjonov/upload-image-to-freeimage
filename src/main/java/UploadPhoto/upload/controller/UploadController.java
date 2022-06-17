package UploadPhoto.upload.controller;

import UploadPhoto.upload.payload.ApiResponse;
import UploadPhoto.upload.payload.FilePath;
import UploadPhoto.upload.payload.ResponseDto;
import UploadPhoto.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;


    @PostMapping("/fromMultipartFile")
    public List<ResponseDto> upload(@RequestPart MultipartFile file) throws IOException {
        return uploadService.uploadPhoto(file);
    }

    @PostMapping("/fromFolder")
    public List<ApiResponse> uploadPhoto(@RequestBody FilePath path) throws IOException {
        return uploadService.uploadPhoto(path);
    }
}
