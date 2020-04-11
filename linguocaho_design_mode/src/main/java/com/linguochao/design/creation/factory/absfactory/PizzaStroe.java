package com.linguochao.design.creation.factory.absfactory;



public class PizzaStroe {
	public static void main(String[] args) {
		OrderPizza mOrderPizza=new OrderPizza(new LDFactory());
		System.out.println(mOrderPizza);
	}
}
