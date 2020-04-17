package com.linguochao.design.action.observer;

import com.linguochao.design.action.observer.observer.CurrentConditions;
import com.linguochao.design.action.observer.observer.ForcastConditions;
import com.linguochao.design.action.observer.subject.WeatherDataSt;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class InternetWeather {

    public static void main(String[] args) {

        CurrentConditions mCurrentConditions;
        ForcastConditions mForcastConditions;
        WeatherDataSt mWeatherDataSt;

        mWeatherDataSt=new WeatherDataSt();
        mCurrentConditions=new CurrentConditions();
        mForcastConditions=new ForcastConditions();

        mWeatherDataSt.registerObserver(mCurrentConditions);
        mWeatherDataSt.registerObserver(mForcastConditions);

        mWeatherDataSt.setData(30, 150, 40);
        mWeatherDataSt.removeObserver(mCurrentConditions);
        mWeatherDataSt.setData(40, 250, 50);
    }

}
