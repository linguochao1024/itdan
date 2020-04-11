package com.linguochao.design.action.strategy.duck;

import com.linguochao.design.action.strategy.flybehavior.GoodFlyBehavior;
import com.linguochao.design.action.strategy.quackbehavior.GaGaQuackBehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class GreenHeadDuck extends Duck {

    public GreenHeadDuck() {
        mFlyBehavior = new GoodFlyBehavior();
        mQuackBehavior = new GaGaQuackBehavior();
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        System.out.println("**GreenHead**");
    }

}
