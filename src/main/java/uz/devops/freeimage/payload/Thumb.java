package uz.devops.freeimage.payload;

import lombok.Data;

@Data
public class Thumb {
    String filename;
    String name;
    String mime;
    String extension;
    String url;
}
