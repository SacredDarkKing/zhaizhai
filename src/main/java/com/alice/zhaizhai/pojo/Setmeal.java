package com.alice.zhaizhai.pojo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐
 */
@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //分类id
    private Long categoryId;


    //套餐名称
    private String name;


    //套餐价格
    private BigDecimal price;


    //状态 0:停用 1:启用
    private Integer status;


    //编码
    private String code;


    //描述信息
    private String description;


    //图片
    private String image;


//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


//    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;


    public void createOrUpdate(Long id) {
        //如果不存在id，则表示需要创建
        //如果存在id，则表示不需要创建，执行更新

        if (id != null) {
            if (this.id == null) {
                setCreateUser(id);
                setCreateTime(LocalDateTime.now());
            }
            setUpdateUser(id);
        }
        setUpdateTime(LocalDateTime.now());
    }
}
