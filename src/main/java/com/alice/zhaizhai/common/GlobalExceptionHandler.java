package com.alice.zhaizhai.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月18日 16:54
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String exMessage = ex.getMessage();
        log.error(exMessage);
        if (exMessage.contains("Duplicate entry")) {
            String s = exMessage.split("\'")[1];
            return R.error(s + " 已存在");
        }
        return  R.error("出现错误");
    }



    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(CustomException ex) {
        String exMessage = ex.getMessage();
        log.error(exMessage);
        return R.error(exMessage);
    }
}
