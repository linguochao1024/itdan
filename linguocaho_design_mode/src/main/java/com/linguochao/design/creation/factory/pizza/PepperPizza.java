package com.linguochao.design.creation.factory.pizza;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class PepperPizza extends Pizza {

    @Override
    public void prepare() {
        super.setname("PepperPizza");
        System.out.println(name+" preparing;");
    }

}
