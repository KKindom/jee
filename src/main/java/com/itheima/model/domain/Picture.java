package com.itheima.model.domain;

public class Picture {
    Integer id;
    Integer uid;
    String pictureaddress;
    String picturedescription;
    String picturetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPictureaddress() {
        return pictureaddress;
    }

    public void setPictureaddress(String pictureaddress) {
        this.pictureaddress = pictureaddress;
    }

    public String getPicturedescription() {
        return picturedescription;
    }

    public void setPicturedescription(String picturedescription) {
        this.picturedescription = picturedescription;
    }

    public String getPicturetime() {
        return picturetime;
    }

    public void setPicturetime(String picturetime) {
        this.picturetime = picturetime;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", uid=" + uid +
                ", pictureaddress='" + pictureaddress + '\'' +
                ", picturedescription='" + picturedescription + '\'' +
                ", picturetime='" + picturetime + '\'' +
                '}';
    }
}
