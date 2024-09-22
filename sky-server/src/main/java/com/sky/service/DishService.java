package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    /**
     * 新城菜品service层
     * @param dishDTO
     */
    public void savedish(DishDTO dishDTO);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult dishpage(DishPageQueryDTO dishPageQueryDTO);


    void deletedish(List<Long> id);
}
