package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDIshMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealDIshMapper setmealDIshMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    public void savedish(DishDTO dishDTO){
        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);
        //插入菜品表
        dishMapper.insert(dish);

        Long dishId=dish.getId();
        //插入和菜品表相关的口味表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertFlavor(flavors);
        }
    }

    @Override
    public PageResult dishpage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deletedish(List<Long> id) {
        //check if the dish is on sale
        for(Long ID: id){
            Dish dish = dishMapper.getByid(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //check if a dish is in a set
        List<Long> setmealId = setmealDIshMapper.getSetIDbyDishID(id);
        if(setmealId!=null&&setmealId.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //start delete by id
        for(Long ID : id){
            dishMapper.deletebyID(ID);
            dishFlavorMapper.deleteFalvorbyid(ID);
        }

    }
}
