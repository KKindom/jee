package com.itheima.service.impl;

import com.itheima.dao.PictureMapper;
import com.itheima.model.domain.Picture;
//import com.itheima.service.IPictureService;
import com.itheima.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PictureServiceImpl implements IPictureService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public List<Picture> listPicture() {
        return pictureMapper.listPicture();
    }

    @Override
    public int savePicture(Picture picture) {
        return pictureMapper.savePicture(picture);
    }

    @Override
    public Picture getPicture(Long id) {
        return pictureMapper.getPicture(id);
    }

    @Override
    public int updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }

    @Override
    public void deletePicture(Long id) {
        pictureMapper.deletePicture(id);
    }

}
