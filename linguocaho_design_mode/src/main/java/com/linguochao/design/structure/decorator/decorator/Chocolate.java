package com.linguochao.design.structure.decorator.decorator;

import com.linguochao.design.structure.decorator.Drink;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class Chocolate extends Decorator {
    public Chocolate(Drink Obj) {
        super(Obj);
        super.setDescription("Chocolate");
        super.setPrice(3.0f);
    }
}
