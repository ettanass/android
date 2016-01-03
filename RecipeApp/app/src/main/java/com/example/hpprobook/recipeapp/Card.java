package com.example.hpprobook.recipeapp;

public class Card {
    private String line1;
    private String line2;
    private String time;
    private String img;
    private String url;

    public Card(String line1, String line2, String time , String img,String url) {
        this.line1 = line1;
        this.line2 = line2;
        this.time = time;
        this.img = img;
        this.url = url;

    }

    public String getLine1() {
        return line1;
    }
    public String getTime() {
        return time;
    }
    public String getImg() {
        return img;
    }
    public String getLine2() {
        return line2;
    }
    public String getUrl() {
        return url;
    }

}
