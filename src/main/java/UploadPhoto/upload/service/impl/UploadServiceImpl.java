package UploadPhoto.upload.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
import UploadPhoto.upload.config.UploadProperties;
import UploadPhoto.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;

import static UploadPhoto.upload.service.UploadService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(
        prefix = "upload",
        name = "simulate",
        havingValue = "false"
)
public class UploadServiceImpl implements UploadService {

    private final UploadProperties uploadProperties;
//    private final RestTemplate uploadRestTemplate;

    @Autowired
    public UploadServiceImpl(UploadProperties uploadProperties/*, @Qualifier("uploadRestTemplate") RestTemplate uploadRestTemplate*/) {
        this.uploadProperties = uploadProperties;
//        this.uploadRestTemplate = uploadRestTemplate;
    }

//    @Override
//    public String send(String request) {
//        log.debug("Request : {}, name : {}", request, uploadProperties.getName());
//        return "Response for : " + request;
//    }

}
