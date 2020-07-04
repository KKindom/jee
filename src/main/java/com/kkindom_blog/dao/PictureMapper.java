package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {
    //查询用户id照片
    @Select("select * from t_collect_picture where u_id = 1")
    public List<Picture> listPicture();

    //查询所有用户照片
    @Select("select * from t_collect_picture")
    public List<Picture> ALLlistPicture();
    //添加图片
    @Insert("insert into t_collect_picture (u_id,pictureaddress,picturedescription,picturetime) values (#{u_id},#{pictureaddress},#{picturedescription},#{picturetime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int savePicture(Picture picture);

    //根据id查询照片
    @Select("select * from t_collect_picture  where id = #{id}")
    public Picture getPicture(Integer id);

    //编辑修改相册
    @Update("update t_collect_picture set pictureaddress = #{pictureaddress}, picturedescription = #{picturedescription} where id = #{id};")
    public int updatePicture(Picture picture);

    //删除照片
    @Delete("delete from t_collect_picture where id = #{id}")
    public void deletePicture(Integer id);

    //模糊查询图片
    @Select("SELECT * FROM t_collect_picture WHERE picturedescription LIKE '%${con}%' ")
    public List<Picture> select_content_withAll_P(@Param("con") String con);
}
