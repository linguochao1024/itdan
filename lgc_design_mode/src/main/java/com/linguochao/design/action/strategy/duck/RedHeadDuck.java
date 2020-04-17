package com.linguochao.design.action.strategy.duck;

import com.linguochao.design.action.strategy.flybehavior.BadFlyBehavior;
import com.linguochao.design.action.strategy.quackbehavior.GeGeQuackBehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class RedHeadDuck extends Duck {

    public RedHeadDuck() {
        mFlyBehavior = new BadFlyBehavior();
        mQuackBehavior = new GeGeQuackBehavior();
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        System.out.println("**RedHead**");
    }

}
