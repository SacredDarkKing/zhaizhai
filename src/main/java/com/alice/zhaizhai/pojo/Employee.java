package com.alice.zhaizhai.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    //序列化为json时，将long转换为string，避免前端精度丢失
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    //转换为json的日期格式化
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

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
