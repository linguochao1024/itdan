package com.linguochao.design.action.strategy.quackbehavior;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public	class NoQuackBehavior implements QuackBehavior
{

    @Override
    public void quack() {
        // TODO Auto-generated method stub
        System.out.println("__NoQuack__");
    }

}
