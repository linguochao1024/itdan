package com.linguochao.design.structure.decorator.decorator;

import com.linguochao.design.structure.decorator.Drink;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public  class Decorator extends Drink {
    private Drink Obj;

    public Decorator(Drink Obj){
        this.Obj=Obj;
    };


    @Override
    public float cost() {
        return super.getPrice()+Obj.cost();
    }

    @Override
    public String getDescription()
    {
        return super.description+"-"+super.getPrice()+"&&"+Obj.getDescription();
    }

}
