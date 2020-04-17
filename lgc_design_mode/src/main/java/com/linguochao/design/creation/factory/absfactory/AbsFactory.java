package com.linguochao.design.creation.factory.absfactory;


import com.linguochao.design.creation.factory.pizza.Pizza;

public interface AbsFactory {
	 Pizza CreatePizza(String ordertype) ;
}
