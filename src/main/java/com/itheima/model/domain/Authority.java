package com.itheima.model.domain;



public class Authority {
    private Integer id;
    private Integer user_id;
    private Integer authority_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getAuthority_id() {
        return authority_id;
    }

    public void setAuthority_id(Integer authority_id) {
        this.authority_id = authority_id;
    }

    @Override
    public String toString() {
        return "authority{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", authority_id=" + authority_id +
                '}';
    }
}
