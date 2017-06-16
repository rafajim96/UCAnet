package com.pdm.ucanet.abstractEntities;

/**
 * Created by Crash on 04/06/2017.
 */

public class Post {
    private String content, date;
    private boolean imgFlag;

    public Post(String content){
        this.content = content;
    }

    public Post(String content, String date, boolean imgFlag) {
        this.content = content;
        this.date = date;
        this.imgFlag = imgFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isImgFlag() {
        return imgFlag;
    }

    public void setImgFlag(boolean imgFlag) {
        this.imgFlag = imgFlag;
    }
}
