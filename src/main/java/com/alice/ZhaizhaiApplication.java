package com.alice;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月01日 16:30
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.alice.zhaizhai.mapper")
@EnableTransactionManagement//开启事务管理
public class ZhaizhaiApplication {
    public static void main(String args[]) {
        SpringApplication.run(ZhaizhaiApplication.class, args);
        log.info("项目启动成功");
    }
}
