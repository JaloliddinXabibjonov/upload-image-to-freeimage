package uz.devops.freeimage.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto {
    int status_code;
    Success success;
    Image image;
    Request request;
    String status_txt;
}
