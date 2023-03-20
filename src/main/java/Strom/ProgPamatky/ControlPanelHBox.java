package graphic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;



public class ControlPanelHBox extends HBox {


    public ControlPanelHBox() {
        setSpacing(12);
        alignmentProperty();
        setAlignment(Pos.CENTER);
        setPrefHeight(70);
        setMaxSize(500,100);
    }

    public void newButton(String nazev, EventHandler<ActionEvent> handler) {
        Button btn = new Button(nazev);
        btn.setPrefSize(90,25);
        btn.setOnAction(handler);
        getChildren().add(btn);

    }


    public void newLabel(String nazev){
        Label label = new Label(nazev);
        getChildren().add(label);
    }
    public TextField newField(String nazev){
        TextField field = new TextField();
        field.setText(nazev);
        field.setPrefSize(90,25);
        field.setEditable(false);
        getChildren().add(field);
        return field;
    }
}
