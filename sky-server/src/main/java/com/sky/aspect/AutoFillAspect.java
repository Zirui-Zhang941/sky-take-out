package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void AutoFillPointCut(){}

    @Before("AutoFillPointCut()")
    public void AutoFill(JoinPoint joinPoint){
        log.info("开始公共字段填充...");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获得签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//从签名获得annotation
        OperationType operationType = autoFill.Value();//获得操作类型

        Object [] args = joinPoint.getArgs();
        Object entity = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        if(operationType == OperationType.INSERT){
            try {
                Method setcreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setcreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setupdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setupdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                //反射赋值
                setcreateTime.invoke(entity,now);
                setcreateUser.invoke(entity,id);
                setupdateTime.invoke(entity,now);
                setupdateUser.invoke(entity,id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType == OperationType.UPDATE) {
            try {
                Method setupdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setupdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                //反射赋值
                setupdateTime.invoke(entity,now);
                setupdateUser.invoke(entity,id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
