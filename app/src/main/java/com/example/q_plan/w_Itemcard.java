package com.example.q_plan;

import java.util.Map;

public class w_Itemcard {

    private String id;
    private String title;
    private String address;
    private String img = "";

    private Userdata user = Userdata.getInstance();
    public w_Itemcard(String id) {
        this.id = id;

        Map<String, String> tmp = user.getContent(id);
        title = tmp.get("title");
        address = tmp.get("addr");
        img = tmp.get("image");

        if(!address.isEmpty())
            address = address.replace("충청남도 ", "").replace("충남 ", "");

        if (img != "")     //https 변경
            img = img.replace("https", "http").replace("http", "https").trim();
        else {
            img = "R.drawable.ic_launcher_background";
        }
    }
    public w_Itemcard(String title, String address){
        this.address = address;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "w_Itemcard{" +
                "title='" + title + '\'' +
                ", contents='" + address + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
// 데이터를 입력받아서 변수에 담고 세팅한다.