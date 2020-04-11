package com.linguochao.design.creation.factory.absfactory;


import com.linguochao.design.creation.factory.pizza.NYCheesePizza;
import com.linguochao.design.creation.factory.pizza.NYPepperPizza;
import com.linguochao.design.creation.factory.pizza.Pizza;

public class NYFactory implements AbsFactory {

	
	@Override
	public Pizza CreatePizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new NYCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new NYPepperPizza();
		}
		return pizza;

	}

}
