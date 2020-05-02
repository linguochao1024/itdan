package com.linguochao.design.structure.facade;

import com.linguochao.design.structure.facade.hometheater.HomeTheaterFacade;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/5/1 22:28
 */
public class MainTest {
    public static void main(String[] args) {
        HomeTheaterFacade mHomeTheaterFacade=new HomeTheaterFacade();

        mHomeTheaterFacade.ready();
        mHomeTheaterFacade.play();
    }
}
