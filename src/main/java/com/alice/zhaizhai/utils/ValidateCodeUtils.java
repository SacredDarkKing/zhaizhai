package com.alice.zhaizhai.utils;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月02日 16:32
 */
public class ValidateCodeUtils {

    //生成长度为len位的验证码
    public static String getCode(int len) {
        String code = "";
        int n;
        for (int i = 0; i < len; i++) {
            n = (int)(Math.random() * 10);
            code += n;
        }
        return code;
    }

}
