package com.linguochao.design.action.strategy.duck;

import com.linguochao.design.action.strategy.flybehavior.FlyBehavior;
import com.linguochao.design.action.strategy.quackbehavior.QuackBehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public abstract class Duck {

    FlyBehavior mFlyBehavior;
    QuackBehavior mQuackBehavior;

    public Duck() {

    }

    public void Fly() {
        mFlyBehavior.fly();
    }

    public void Quack() {
        mQuackBehavior.quack();
    }

    public abstract void display();

    public void SetQuackBehavoir(QuackBehavior qb) {
        mQuackBehavior = qb;
    }

    public void SetFlyBehavoir(FlyBehavior fb) {
        mFlyBehavior = fb;
    }

    public void swim() {
        System.out.println("~~im swim~~");
    }
}

