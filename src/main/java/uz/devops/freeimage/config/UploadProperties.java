package uz.devops.freeimage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    private String url;
    private String imageKey;

    /* TEST ONLY */
    private String otherFilesFolderUrl;
    private String imageFolderUrl;
    private String emptyFolderUrl;
}
