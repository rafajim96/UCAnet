package com.pdm.ucanet.abstractEntities;

/**
 * Created by Crash on 04/06/2017.
 */

public class Post {
    private int id;
    private String content, date, user, userName, imageName;
    private boolean imgFlag, op;


    public Post(String content){
        this.content = content;
    }

    public Post(int id, String content, String date, String user, boolean imgFlag, boolean op, String userName, String imageName) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.user = user;
        this.imgFlag = imgFlag;
        this.op = op;
        this.userName = userName;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isImgFlag() {
        return imgFlag;
    }

    public void setImgFlag(boolean imgFlag) {
        this.imgFlag = imgFlag;
    }


    public boolean isOp() {
        return op;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOp(boolean op) {
        this.op = op;


    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
