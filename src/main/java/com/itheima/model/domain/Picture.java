package com.itheima.model.domain;

public class Picture {
    Integer id;
    Integer u_id;
    String pictureaddress;
    String picturedescription;
    String picturetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
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
                ", u_id=" + u_id +
                ", pictureaddress='" + pictureaddress + '\'' +
                ", picturedescription='" + picturedescription + '\'' +
                ", picturetime='" + picturetime + '\'' +
                '}';
    }
}
