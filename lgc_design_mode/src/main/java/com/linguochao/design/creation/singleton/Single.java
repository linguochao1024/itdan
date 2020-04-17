package com.linguochao.design.creation.singleton;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class Single {

    private Single(){ }

    static Single s=new Single();

    public static Single getInstance(){  return s;  }
}
