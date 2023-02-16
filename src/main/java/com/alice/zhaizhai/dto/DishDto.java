package com.alice.zhaizhai.dto;

import com.alice.zhaizhai.pojo.Dish;
import com.alice.zhaizhai.pojo.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    @Override
    public String toString() {
        return "\nDishDto{" +
                super.toString() +
                "flavors=" + flavors +
                ", categoryName='" + categoryName + '\'' +
                ", copies=" + copies +
                '}' + '\n';
    }

    @Override
    public void createOrUpdate(Long id) {
        super.createOrUpdate(id);
        Long updateId = this.getUpdateUser();

        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.createOrUpdate(updateId);
        }
    }
}
