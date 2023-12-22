package com.malveillance.uberif;

import java.util.List;
import java.util.Observable;

/**
 * The class represents an observable for the map data.
 */
public class MapDataObservable extends Observable {

    /**
     * Constructs a new MapDataObservable.
     */
    public void dataChanged(List<Object> mapElems) {
        setChanged();
        notifyObservers(mapElems);
    }

}
