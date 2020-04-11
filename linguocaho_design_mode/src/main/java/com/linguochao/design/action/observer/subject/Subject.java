package com.linguochao.design.action.observer.subject;

import com.linguochao.design.action.observer.observer.Observer;

/**
 * description
 *
 * @author linguochao
 * @date 2020\4\11 0011
 */
public interface Subject {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}

