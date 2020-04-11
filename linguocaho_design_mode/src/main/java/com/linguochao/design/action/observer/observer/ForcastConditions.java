package com.linguochao.design.action.observer.observer;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class ForcastConditions implements Observer {
    private float mTemperatrue;
    private float mPressure;
    private float mHumidity;
    @Override
    public void update(float mTemperatrue, float mPressure, float mHumidity) {
        // TODO Auto-generated method stub
        this.mTemperatrue=mTemperatrue;
        this.mPressure=mPressure;
        this.mHumidity=mHumidity;

        display();
    }
    public void display()
    {
        System.out.println("**明天温度:"+(mTemperatrue+Math.random())+"**");
        System.out.println("**明天气压:"+(mPressure+10*Math.random())+"**");
        System.out.println("**明天湿度:"+(mHumidity+Math.random())+"**");
    }
}

