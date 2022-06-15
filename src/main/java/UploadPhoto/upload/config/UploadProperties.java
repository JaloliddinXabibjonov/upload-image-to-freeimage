package UploadPhoto.upload.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    private String url = "http://www.your-url.com";
//    private String username = "my-login";
//    private String password = "change-me";

}
