package com.malveillance.uberif;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.IntersectionService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.view.GraphicalView;
import com.malveillance.uberif.view.KeyboardHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the scene and stage
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("UBER'IF");
        stage.setScene(scene);
        stage.show();


        // Services
        CityMapService cityMapService = new CityMapService() ;
        PaneService paneService = new PaneService();
        IntersectionService intersectionService = new IntersectionService(paneService);

        // Controllers
        CityMapController cityMapController = new CityMapController(cityMapService);
        PaneController paneController = new PaneController(paneService);

        // View
//        GraphicalView view = new GraphicalView();
//        view.setCityMapController(cityMapController);
//        view.setPaneController(paneController);


    }




    public static void main(String[] args) {
        launch();
    }
}
