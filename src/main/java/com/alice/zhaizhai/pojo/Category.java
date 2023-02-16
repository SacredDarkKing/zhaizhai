package com.alice.zhaizhai.pojo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    //类型 1 菜品分类 2 套餐分类
    private Integer type;


    //分类名称
    private String name;


    //顺序
    private Integer sort;


    //创建时间
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //更新时间
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    //创建人
//    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //修改人
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
