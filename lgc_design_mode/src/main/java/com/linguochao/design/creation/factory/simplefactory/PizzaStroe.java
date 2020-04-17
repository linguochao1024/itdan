package com.linguochao.design.creation.factory.simplefactory;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class PizzaStroe {
    public static void main(String[] args) {
        OrderPizza orderPizza = new OrderPizza(new SimplePizzaFactory());
        System.out.println(orderPizza);
    }
}
