package Strom.ProgPamatky;

import Strom.Model.GPS;
import Strom.Model.Zamek;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ProcesDialog extends Stage {
    private TextField id;
    private TextField nazev;
    private TextField GPSx;
    private TextField GPSy;
    private final Zamek zamek;
    private Zamek finalZamek;
    private int pocet;
    private GridPane gridPane;

    public ProcesDialog(Zamek zamek) {
        this.zamek = zamek;
        this.pocet=0;
        this.finalZamek = zamek;
        setTitle("Zamek");
        setResizable(false);
        setScene(start());
    }
    public Scene start(){

        VBox vBox = new VBox(12);
        vBox.setAlignment(Pos.CENTER);
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(7);
        gridPane.setVgap(20);
        vBox.getChildren().addAll(gridPane);
        id = addRow("Id:", pocet++, "");
        nazev = addRow("Nazev:", pocet++, "");
        GPSx = addRow("GPS-x:", pocet++, "");
        GPSy = addRow("GPS-y:", pocet++, "");
        Button ok = new Button("OK");
        Button cancel = new Button("CANCEL");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPrefHeight(50);
        hBox.setSpacing(12);
        cancel.setPrefSize(75,25);
        ok.setPrefSize(75,25);
        hBox.getChildren().addAll(ok,cancel);
        cancel.setOnAction(actionEvent -> {
            finalZamek = null;
            hide();
        });
        ok.setOnAction(actionEvent -> {
            if(obnov())hide();
        });
        vBox.getChildren().add(hBox);
        return new Scene(vBox);
    }

    protected TextField addRow(String nazev, int radek,String atribut) {
        gridPane.add(new Label(nazev), 0, radek);
        TextField textField = new TextField(atribut);
        textField.setEditable(true);
        gridPane.add(textField, 1, radek);
        return textField;
    }

    private boolean obnov() {
        try {
            if (!(id.getText().equals("") || nazev.getText().equals("")  || GPSx.getText().equals("") || GPSy.getText().equals(""))) {
                finalZamek.setId(Integer.parseInt(id.getText()));
                finalZamek.setNazev(nazev.getText());
                finalZamek.setGps(new GPS(Integer.parseInt(GPSx.getText()), Integer.parseInt(GPSy.getText())));
                return true;
            } else {
                new Alert(Alert.AlertType.WARNING, "Vyplňte všechna pole", ButtonType.OK).show();
                return false;
            }
        }catch (NumberFormatException e) {
            new Alert(Alert.AlertType.INFORMATION,"Zadejte prosim cisla jako souradnice",ButtonType.OK).show();
            return false;
        }
    }

    public Zamek vratit(){
        return finalZamek;
    }
}