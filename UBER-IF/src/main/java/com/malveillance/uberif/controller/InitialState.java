package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;

public class InitialState implements State {

    public InitialState(){};

    @Override
    public void delete(PaneController paneController){};

    @Override
    public void undo(){};

    @Override
    public void redo(){};

    @Override
    public void delete(){};

    @Override
    public void save(CityMap cityMap){};

    @Override
    public void load(CityMap cityMap){};

}