module com.malveillance.uberif {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.malveillance.uberif to javafx.fxml;
    exports com.malveillance.uberif;
}