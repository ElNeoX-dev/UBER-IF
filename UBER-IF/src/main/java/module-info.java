module com.malveillance.uberif {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.malveillance.uberif to javafx.fxml;
    exports com.malveillance.uberif;
    exports com.malveillance.uberif.controller;
    opens com.malveillance.uberif.controller to javafx.fxml;
    exports com.malveillance.uberif.xml;
    opens com.malveillance.uberif.xml to javafx.fxml;
}