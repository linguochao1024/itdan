package com.linguochao.design.creation.factory.simplefactory;

import com.linguochao.design.creation.factory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class OrderPizza {
    SimplePizzaFactory mSimplePizzaFactory;

    public OrderPizza(SimplePizzaFactory mSimplePizzaFactory) {
        setFactory(mSimplePizzaFactory);
    }

    public void setFactory(SimplePizzaFactory mSimplePizzaFactory) {
        Pizza pizza;
        String ordertype;

        this.mSimplePizzaFactory = mSimplePizzaFactory;

        do {
            ordertype = gettype();
            pizza = mSimplePizzaFactory.CreatePizza(ordertype);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }
        } while (true);

    }

    private String gettype() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}

