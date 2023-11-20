package com.malveillance.uberif;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("UBER'IF");
        stage.setScene(scene);
        stage.show();

        /*
        XmlMapParser parser = new XmlMapParser();

        ListView<String> listView = new ListView<>();

        // Parse the XML file and add items to the ListView
        List<Object> mapElements = parser.parseXmlFile("src/main/resources/com/malveillance/uberif/smallMap.xml");
        mapElements.forEach(element -> listView.getItems().add(element.toString()));

        Scene scene = new Scene(listView, 600, 400);
        stage.setScene(scene);
        stage.setTitle("XML Map Viewer");
        stage.show();
        */
    }

    public static void main(String[] args) {
        launch();
    }
}