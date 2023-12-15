module com.malveillance.uberif {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;

    opens com.malveillance.uberif to javafx.fxml;
    exports com.malveillance.uberif;
    exports com.malveillance.uberif.view;
    opens com.malveillance.uberif.view to javafx.fxml;
    exports com.malveillance.uberif.xml;
    opens com.malveillance.uberif.xml to javafx.fxml;
    exports com.malveillance.uberif.model;
    opens com.malveillance.uberif.model to javafx.fxml;
}