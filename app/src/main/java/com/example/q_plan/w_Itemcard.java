package com.example.q_plan;

import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class w_Itemcard {

    private String id;
    private String title;
    private String contents;
    private String img = "";

    private Userdata user = Userdata.getInstance();
    public w_Itemcard(String id) {
        this.id = id;

        Map<String, String> tmp = user.getContent(id);
        System.out.println(tmp.toString());
        title = tmp.get("title");
        contents = tmp.get("addr");
        img = tmp.get("image");

        if(!contents.isEmpty())
            contents = contents.replace("충청남도 ", "").replace("충남 ", "");

        if (img != "")     //https 변경
            img = img.replace("https", "http").replace("http", "https").trim();
        else {
            img = "R.drawable.ic_launcher_background";
        }
    }
    public w_Itemcard(String title, String contents){
        this.contents = contents;
        this.title = title;
    }

    public String getId() {
        return id;
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