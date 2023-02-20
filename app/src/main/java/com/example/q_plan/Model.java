package com.example.q_plan;

//이미지 업로드용
public class Model {
    private String imageUrl;

    Model(){}

    public Model(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
