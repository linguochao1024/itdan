package com.linguochao.design.structure.adapter.adapter;


import com.linguochao.design.structure.adapter.duck.Duck;
import com.linguochao.design.structure.adapter.turkey.WildTurkey;

public class TurkeyAdapter2 extends WildTurkey implements Duck {

	@Override
	public void quack() {
		super.gobble();
	}
	@Override
	public void fly() {
		super.fly();
		super.fly();
		super.fly();
	}
}
