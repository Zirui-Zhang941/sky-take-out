package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味数据
     * @param flavors
     */
    void insertFlavor(List<DishFlavor> flavors);

    /**
     * delete falvor by dish id
     * @param id
     */
    @Delete("delete from dish_falvor where dish_id = #{id}")
    public void deleteFalvorbyid(Long id);
}
