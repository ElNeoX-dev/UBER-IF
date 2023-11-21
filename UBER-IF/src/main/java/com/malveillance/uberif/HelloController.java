package com.malveillance.uberif;

import com.malveillance.uberif.utils.CustomColors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class HelloController {
    int nbCouriers = 1;

    @FXML
    private Label nbCourierLb;

    @FXML
    private Button minusBtn;

    @FXML
    private ChoiceBox choiceMap;

    @FXML
    protected void onPlusBtnClick() {
        if (nbCouriers == 1) {
            minusBtn.getStyleClass().remove("grey-state");
            minusBtn.getStyleClass().add("blue-state");
        }

        nbCouriers++;
        nbCourierLb.setText(String.valueOf(nbCouriers));
    }

    @FXML
    protected void onMinusBtnClick() {
        if (nbCouriers > 1) {
            nbCouriers--;
            nbCourierLb.setText(String.valueOf(nbCouriers));
        }
        if (nbCouriers == 1) {
            minusBtn.getStyleClass().remove("blue-state");
            minusBtn.getStyleClass().add("grey-state");
        }
    }

    @FXML
    protected void onImportDataBtnClick() {
        System.out.println("Import data click");

    }

    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");

    }

    @FXML
    protected void onSaveBtnClick() {
        System.out.println("Save click");

    }

    @FXML
    protected void onRestoreBtnClick() {
        System.out.println("Restore click");

    }

    public void initialize() {
        minusBtn.getStyleClass().add("grey-state");

        // Create a list of choices
        ObservableList<String> mapChoices
                = FXCollections.observableArrayList("smallMap", "mediumMap", "largeMap");

        // Set the choices in the ChoiceBox
        choiceMap.setItems(mapChoices);

        // Optionally, set a default selected item
        choiceMap.getSelectionModel().selectFirst();

        choiceMap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected item changed from " + oldValue + " to " + newValue);

            }
        });
    }
}