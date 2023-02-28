package com.example.q_plan;

import java.util.Objects;

public class w_Itemcard {

    private String title;
    private String contents;

    public w_Itemcard(String title, String contents){
        this.contents = contents;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "w_Itemcard{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}