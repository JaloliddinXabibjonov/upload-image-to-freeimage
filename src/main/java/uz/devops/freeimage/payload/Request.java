package uz.devops.freeimage.payload;

import lombok.Data;

@Data
public class Request {
    String type;
    String action;
    String key;
}
