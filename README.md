# zhaizhai
*   基于SpringBoot的宅宅外卖基本介绍：
    *   该项目是为单个商家服务的web应用，分为<font color="green">客户端</font>和<font color="green">管理端</font>。
    *   用户可以在<font color="green">客户端</font>登录、浏览菜单、操作购物车、下单。
    *   <font color="red">商家以及商家的员工</font>可以登录<font color="green">管理端</font>，登陆后可以实现以这几个方面的管理：员工、分类、菜品、套餐、订单。
    *   目前项目**已经部署并经过测试**，可以通过下方的超链接访问（初次加载浏览器会缓存，需要稍微的等待）。

[宅宅外卖客户端（请用手机浏览器访问，或者通过pc浏览器调试模式开启设备仿真）](http://1.15.142.160/front/page/login.html)

[宅宅外卖管理端](http://1.15.142.160/backend/page/login/login.html)

[Github项目源码地址](https://github.com/SacredDarkKing/zhaizhai)

1.  项目整体功能:
    1.  客户端：
        1.  登录
        2.  菜品分类、套餐分类
        3.  菜品、套餐详情
        4.  购物车添加、减少、清空
        5.  地址添加、编辑
        6.  订单提交、查看
    2.  管理端：
        1.  登录

        2.  员工管理

        3.  分类管理

        4.  菜品管理

        5.  套餐管理

        6.  订单管理
2.  服务器配置：
    1.  Mysql主从复制
    2.  Redis缓存
    3.  Nginx部署静态资源，并且反向代理到Tomcat的web应用程序


