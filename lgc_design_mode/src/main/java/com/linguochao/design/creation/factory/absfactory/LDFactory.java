package com.linguochao.design.creation.factory.absfactory;

import com.linguochao.design.creation.factory.pizza.LDCheesePizza;
import com.linguochao.design.creation.factory.pizza.LDPepperPizza;
import com.linguochao.design.creation.factory.pizza.Pizza;

public class LDFactory implements AbsFactory {

	@Override
	public Pizza CreatePizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;

	}

}
