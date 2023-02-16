package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.R;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月25日 15:21
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${zhaizhai.img-path}")
    private String imgPath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，如果本次处理不做操作，则请求响应完成后会自动删除该文件
        String originalFilename = file.getOriginalFilename();//原始文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString() + suffix;
        log.info("存储文件：{}",filename);

        //file.transferTo不会自动创建目录，需要判断是否存在，不存在需要手动创建
        File path = new File(imgPath);
        if (!path.exists()) {
            path.mkdirs();
        }

        try {
            file.transferTo(new File(imgPath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        if (StringUtil.isEmpty(name))
            return;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imgPath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            byte[] bytes = new byte[64];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            outputStream.close();
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
