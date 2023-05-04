package com.example.q_plan;

public class w_Itemcard {

    private String title;
    private String contents;
    private String img = "";

    public w_Itemcard(String title, String contents){
        this.contents = contents;
        this.title = title;
    }

    public w_Itemcard(String title, String contents, String img){
        this.title = title;

        if (!img.isEmpty())     //https 변경
            this.img = new StringBuffer(img).insert(4, "s").toString();

        if(!contents.isEmpty())
            this.contents = contents.substring(9);
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "w_Itemcard{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
// 데이터를 입력받아서 변수에 담고 세팅한다.