package UploadPhoto.upload.payload;

import lombok.Data;

@Data
public class Image {
    String name;
    String extension;
    String width;
    String height;
    int size;
    String time;
    String expiration;
    String adult;
    String status;
    String cloud;
    String likes;
    String vision;
    String description;
    String original_exifdata;
    String original_filename;
    String views_html;
    String views_hotlink;
    String access_html;
    String access_hotlink;
    File file;

    int is_animated;
    int nsfw;
    String id_encoded;
    double ratio;
    String size_formatted;
    String filename;
    String url;
    String url_short;
    String url_seo;
    String url_viewer;
    String url_viewer_preview;
    String url_viewer_thumb;
    ImageObject image;
    Thumb thumb;
    Thumb medium;
    String display_url;
    String display_width;
    String display_height;
    String views_label;
    String likes_label;
    String how_long_ago;
    String date_fixed_peer;
    String title;
    String title_truncated;
    String title_truncated_html;
    boolean is_use_loader;


}
