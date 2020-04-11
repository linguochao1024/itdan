package com.linguochao.design.structure.decorator.coffee;

import com.linguochao.design.structure.decorator.Drink;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public  class Coffee extends Drink {

    @Override
    public float cost() {
        // TODO Auto-generated method stub
        return super.getPrice();
    }


}
