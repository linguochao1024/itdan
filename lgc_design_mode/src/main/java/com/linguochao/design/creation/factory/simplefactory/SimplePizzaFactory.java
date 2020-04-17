package com.linguochao.design.creation.factory.simplefactory;

import com.linguochao.design.creation.factory.pizza.CheesePizza;
import com.linguochao.design.creation.factory.pizza.GreekPizza;
import com.linguochao.design.creation.factory.pizza.PepperPizza;
import com.linguochao.design.creation.factory.pizza.Pizza;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class SimplePizzaFactory {

    public Pizza CreatePizza(String ordertype) {
        Pizza pizza = null;

        if (ordertype.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (ordertype.equals("greek")) {
            pizza = new GreekPizza();
        } else if (ordertype.equals("pepper")) {
            pizza = new PepperPizza();
        }
        return pizza;

    }

}
