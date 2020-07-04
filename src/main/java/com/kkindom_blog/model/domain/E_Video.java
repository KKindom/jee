package com.kkindom_blog.model.domain;

import lombok.Data;

import java.util.Date;

@Data
public class E_Video
{
    private Integer id;
    private String code;
    private Date created;
    private String title;
    private Integer eid;
}
