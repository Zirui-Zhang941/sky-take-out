package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDIshMapper {
    /**
     * search setmeal id by dish id
     * @param dishid list of long
     * @return List of Long
     */
    List<Long> getSetIDbyDishID(List<Long> dishid);
}
