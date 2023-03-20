package Strom.ProgPamatky;

import Strom.enun.eTypProhl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControlPanelVbox extends VBox {

    public ControlPanelVbox() {
        setSpacing(25);
        setMaxSize(100,500);
        alignmentProperty();
        setAlignment(Pos.CENTER);
        setPrefWidth(100);

    }
    public void newField(String nazev,EventHandler<ActionEvent> handler){
        TextField field = new TextField();
        field.setPromptText(nazev);
        field.setPrefSize(80,25);
        field.setOnAction(handler);
        getChildren().add(field);
    }
    public void newComboBox(String nazev, EventHandler<ActionEvent> handler){
        ObservableList<eTypProhl> list= FXCollections.observableArrayList(eTypProhl.values());
        ComboBox<eTypProhl> comboBox = new ComboBox<>(list);
        comboBox.setPrefSize(95,25);
        comboBox.setPromptText(nazev);
        comboBox.setOnAction(handler);
        comboBox.setValue(eTypProhl.HLOUBKA);
        getChildren().add(comboBox);
    }

    public void newButton(String nazev, EventHandler<ActionEvent> handler) {
        Button btn = new Button(nazev);
        btn.setPrefSize(80,25);
        btn.setOnAction(handler);
        getChildren().add(btn);
    }
    public void newLabel(String nazev){
        Label label = new Label(nazev);
        getChildren().add(label);


    }
}
