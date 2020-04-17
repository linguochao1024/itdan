package com.linguochao.design.creation.singleton;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public class LazySingle {

    private static LazySingle s=null ;

    private LazySingle(){ }

    public static LazySingle getInstance() {
        if (s == null) {
            synchronized (LazySingle.class) {
                if (s == null){
                    s = new LazySingle();
                }
            }
        }
        return s;
    }

}
