package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.User;
import com.alice.zhaizhai.service.UserService;
import com.alice.zhaizhai.utils.SMSUtils;
import com.alice.zhaizhai.utils.ValidateCodeUtils;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 13:00
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private ValidateCodeUtils validateCodeUtils;
    //todo: 管理端控制器的数据接收校验

    @Autowired
    private SMSUtils smsUtils;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtil.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.getCode(4);
            log.info("手机号{}申请发送验证码：{}", phone, code);

            //将code保存到session，用于校验
            session.setAttribute("code", code);
            //todo: 正式部署需要打开短信发送
            smsUtils.sendSMS(code, 24, phone);
            return R.success(code);
        }

        return R.error("发送失败");
    }


    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> map, HttpSession session) {
        String inputCode = map.get("code");
        String phone = map.get("phone");
        Object code = session.getAttribute("code");

        //未发送验证码
        if (code == null)
            return R.error("请发送验证码");

        //传递的数据出错
        if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(inputCode))
            return R.error("数据错误");

        //验证码不对
        if (!inputCode.equals(code))
            return R.error("验证码错误");

        //获取用户信息
        User user = userService.getByPhone(phone);

        //如果查询的用户为空，则表示是新用户，执行注册用户操作
        if (user == null) {
            //设置用户手机号
            user = new User();
            user.setPhone(phone);
            userService.save(user);//注册
            //将用户id保存到session
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        //用户被禁用，不允许登录
        if (user.getStatus() == 0)
            return R.error("账户被禁用");


        //用户不为空，且账户未被禁用，将用户id保存到session，返回登录成功
        session.setAttribute("user", user.getId());

        return R.success(user);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return R.success("登出成功");
    }
}
