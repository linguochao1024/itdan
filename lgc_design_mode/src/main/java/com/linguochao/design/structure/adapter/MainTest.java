package com.linguochao.design.structure.adapter;

import com.linguochao.design.structure.adapter.adapter.TurkeyAdapter2;
import com.linguochao.design.structure.adapter.duck.Duck;
import com.linguochao.design.structure.adapter.duck.GreenHeadDuck;
import com.linguochao.design.structure.adapter.turkey.WildTurkey;

public class MainTest {
	public static void main(String[] args) {
		GreenHeadDuck duck=new GreenHeadDuck();
		
		WildTurkey turkey=new WildTurkey();
		
		Duck duck2turkeyAdapter=new TurkeyAdapter2();
		turkey.gobble();
		turkey.fly();
		duck.quack();
		duck.fly();
		duck2turkeyAdapter.quack();
		duck2turkeyAdapter.fly();
		
	
	}
}
