package com.linguochao.design.structure.decorator.decorator;

import com.linguochao.design.structure.decorator.Drink;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class Soy extends Decorator {

    public Soy(Drink Obj) {
        super(Obj);
        super.setDescription("Soy");
        super.setPrice(1.5f);
    }

}

