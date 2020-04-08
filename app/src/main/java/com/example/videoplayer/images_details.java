package com.example.videoplayer;

public class images_details {
    private String title;
    private String size;

    private String path;
    private int image_id;

    public images_details(String title, String size, String path, int image_id) {
        this.title = title;
        this.size = size;
        this.path = path;
        this.image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
