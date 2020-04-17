package com.linguochao.design.structure.decorator;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public abstract class Drink {
    public String description="";
    private float price=0f;;


    public void setDescription(String description)
    {
        this.description=description;
    }

    public String getDescription()
    {
        return description+"-"+this.getPrice();
    }
    public float getPrice()
    {
        return price;
    }
    public void setPrice(float price)
    {
        this.price=price;
    }
    public abstract float cost();

}
