package uz.devops.freeimage.payload;

import lombok.Data;

@Data
public class ImageObject {
    String filename;
    String name;
    String mime;
    String extension;
    String url;
    int size;
}
