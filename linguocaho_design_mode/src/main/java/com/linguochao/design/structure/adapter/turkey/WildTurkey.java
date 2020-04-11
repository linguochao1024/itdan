package com.linguochao.design.structure.adapter.turkey;

public class WildTurkey implements Turkey {

	@Override
	public void gobble() {
		System.out.println(" Go Go");
	}

	@Override
	public void fly() {
		System.out.println("I am flying a short distance");
	}

}
