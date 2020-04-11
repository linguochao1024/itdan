package com.linguochao.design.action.strategy;

import com.linguochao.design.action.strategy.duck.Duck;
import com.linguochao.design.action.strategy.duck.GreenHeadDuck;
import com.linguochao.design.action.strategy.duck.RedHeadDuck;
import com.linguochao.design.action.strategy.flybehavior.NoFlyBehavior;
import com.linguochao.design.action.strategy.quackbehavior.NoQuackBehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class StimulateDuck {

    public static void main(String[] args) {

        Duck mGreenHeadDuck = new GreenHeadDuck();
        Duck mRedHeadDuck = new RedHeadDuck();

        mGreenHeadDuck.display();
        mGreenHeadDuck.Fly();
        mGreenHeadDuck.Quack();
        mGreenHeadDuck.swim();

        mRedHeadDuck.display();
        mRedHeadDuck.Fly();
        mRedHeadDuck.Quack();
        mRedHeadDuck.swim();
        mRedHeadDuck.display();
        mRedHeadDuck.SetFlyBehavoir(new NoFlyBehavior());
        mRedHeadDuck.Fly();
        mRedHeadDuck.SetQuackBehavoir(new NoQuackBehavior());
        mRedHeadDuck.Quack();
    }

}
