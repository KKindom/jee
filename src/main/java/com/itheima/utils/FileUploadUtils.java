package com.itheima.utils;

import com.itheima.model.domain.AttachFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 功能：用于管理上传的文件
 *
 * @author
 */
public class FileUploadUtils {
    /**
     * @param uploadDir 文件保存的路径
     * @param file 上传的文件
     * @return AttachFile文件对象
     * @throws Exception
     */
    public static AttachFile upload(String uploadDir, MultipartFile file,int id) throws IOException {
        //获取文件保存路径
    File dir=new File(uploadDir);
    if(!dir.exists())
    {
        dir.mkdirs();
    }
    AttachFile attachFile=null;
    String filename=""+id+".jpg";
    if(!file.isEmpty())
    {
        attachFile =new AttachFile();
        File newfile=new File(dir,filename);
        file.transferTo(newfile);
        attachFile.setOriginalFilename(file.getOriginalFilename());
        attachFile.setFilename(filename);
        attachFile.setType(file.getContentType());
        attachFile.setFileSize(file.getSize());
        attachFile.setFileUUID(UUID.randomUUID().toString());
        attachFile.setUploadTime(new Date());
        System.out.println("文件"+attachFile+"上传成功！");
    }
    return attachFile;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
