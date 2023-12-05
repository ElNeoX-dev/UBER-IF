package com.malveillance.uberif;

import java.util.List;
import java.util.Observable;

public class MapDataObservable extends Observable {

    public void dataChanged(List<Object> mapElems) {
        setChanged();
        notifyObservers(mapElems);
    }

}
