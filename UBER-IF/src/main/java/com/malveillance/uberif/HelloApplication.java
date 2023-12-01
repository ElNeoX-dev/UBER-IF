package com.malveillance.uberif;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.IntersectionService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.view.GraphicalView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 650);
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
