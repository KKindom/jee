package com.kkindom_blog.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * 上传文件的相关信息
 * @author
 */
@Data
public class AttachFile {
    /**
     * 上传文件的UUID
     */
    String fileUUID;
    /**
     * 保存后的文件名
     */
    String filename;
    String  originalFilename;
    /**
     *  文件大小
     */
    long fileSize;
    /**
     * 上传时间
     */
    Date uploadTime;
    /**
     * 文件类型
     */
    String type;
}
