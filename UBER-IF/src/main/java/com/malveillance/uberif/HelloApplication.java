package com.malveillance.uberif;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.view.GraphicalView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;

/**
 * JavaFX App
 */
public class HelloApplication extends Application {

    /**
     * The graphical view.
     */
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

        // Controllers
        CityMapController cityMapController = new CityMapController(cityMapService);
        PaneController paneController = new PaneController(paneService);


    }

    /**
     * Launches the application.
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
