package com.alice.zhaizhai.pojo;


import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
菜品口味
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品id
    private Long dishId;


    //口味名称
    private String name;


    //口味数据list
    private String value;


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
