package com.linguochao.design.creation.factory.pizza;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class CheesePizza extends Pizza {

    @Override
    public void prepare() {
        super.setname("CheesePizza");
        System.out.println(name+" preparing;");
    }

}
