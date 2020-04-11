package com.linguochao.design.structure.decorator;

import com.linguochao.design.structure.decorator.coffee.Decaf;
import com.linguochao.design.structure.decorator.coffee.LongBlack;
import com.linguochao.design.structure.decorator.decorator.Chocolate;
import com.linguochao.design.structure.decorator.decorator.Milk;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class CoffeeBar {


    public static void main(String[] args) {

        Drink order;
        order=new Decaf();
        System.out.println("order1 price:"+order.cost());
        System.out.println("order1 desc:"+order.getDescription());

        System.out.println("****************");
        order=new LongBlack();
        order=new Milk(order);
        order=new Chocolate(order);
        order=new Chocolate(order);
        System.out.println("order2 price:"+order.cost());
        System.out.println("order2 desc:"+order.getDescription());

    }

}

