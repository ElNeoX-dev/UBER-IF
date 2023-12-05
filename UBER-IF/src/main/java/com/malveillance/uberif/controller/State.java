package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

public interface State {

    public default void delete(PaneController paneController){};
    public default void undo(){};
    public default void redo(){};
    public default void delete(){};
    public default void save(CityMap cityMap){};
    public default void load(CityMap cityMap){};
    public default void leftClick(PaneController paneController, Intersection intersection, CityMap cityMap){};

}
