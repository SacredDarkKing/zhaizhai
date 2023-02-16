package com.alice.zhaizhai.dto;

import com.alice.zhaizhai.pojo.Setmeal;
import com.alice.zhaizhai.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;


    @Override
    public void createOrUpdate(Long id) {
        super.createOrUpdate(id);
        Long updateId = this.getUpdateUser();

        for(SetmealDish setmealDish : setmealDishes) {
            setmealDish.createOrUpdate(updateId);
        }
    }
}
