package com.linguochao.design.action.strategy.flybehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class	NoFlyBehavior implements FlyBehavior
{
    @Override
    public void fly() {
        // TODO Auto-generated method stub
        System.out.println("--NoFly--");
    }
}

