package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api("新增菜品")
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result savedish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.savedish(dishDTO);
        return Result.success();
    }


    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> dishpage(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.dishpage(dishPageQueryDTO);
        log.info("菜品分页查询：{}",pageResult);
        return Result.success(pageResult);
    }

    /**
     * delete the dish with specified id
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete_Dish")
    public Result deletedish(@RequestParam List<Long> id){
        log.info("delete dish id:{}",id);
        dishService.deletedish(id);
        return Result.success();
    }
}
