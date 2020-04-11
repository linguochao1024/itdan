package com.linguochao.design.structure.decorator.decorator;

import com.linguochao.design.structure.decorator.Drink;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class Milk extends Decorator {

    public Milk(Drink Obj) {
        super(Obj);
        super.setDescription("Milk");
        super.setPrice(2.0f);
    }

}
