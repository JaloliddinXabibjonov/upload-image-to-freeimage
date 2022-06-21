package uz.devops.freeimage.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePath {
    private String path;

    public FilePath() {
    }

    public FilePath(String path) {
        this.path = path;
    }
}
