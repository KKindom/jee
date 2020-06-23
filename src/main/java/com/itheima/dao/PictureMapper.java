package com.itheima.dao;

import com.itheima.model.domain.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {
    //查询照片
    @Select("select * from t_collect_picture order by id desc")
    List<Picture> listPicture();

    //添加图片
    @Insert("insert into t_collect_picture (u_id,pictureaddress,picturedescription,picturetime) values (#{u_id},#{pictureaddress},#{picturedescription},#{picturetime})")
    int savePicture(Picture picture);

    //根据id查询照片
    @Select("select * from t_collect_picture  where id = #{id}")
    Picture getPicture(Long id);

    //编辑修改相册
    @Update("update t_collect_picture set pictureaddress = #{pictureaddress}, picturedescription = #{picturedescription} where id = #{id};")
    int updatePicture(Picture picture);

    //删除照片
    @Delete("delete from t_collect_picture where id = #{id}")
    void deletePicture(Long id);

}
