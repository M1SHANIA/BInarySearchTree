package Strom.ProgPamatky;


import Strom.Model.Zamek;
import Strom.Pamatky.Pamatky;
import Strom.Table.BinStromException;
import Strom.enun.eTypProhl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Optional;

import static Strom.enun.eTypKey.GPS;
import static Strom.enun.eTypKey.NAZEV;

public class MainFX extends Application {

    private Pamatky pamatky;
    private ListView<Zamek> langsListView;
    private TextField field;
    private eTypProhl typProhl;


    public static void main(String[] args) {
        launch(args);
    }


    private ControlPanelVbox comandsV(){
        var controlV = new ControlPanelVbox();
        controlV.newButton("FIRST", nastavPrvni());
        controlV.newButton("NEXT", nastavDalsi());
        controlV.newButton("PREVIOUS", nastavPred());
        controlV.newButton("LAST", nastavPosledni());
        controlV.newButton("DELETE", vymaz());
        controlV.newButton("NAJDI NEJBLIZ",najdiNejblizsi());
        controlV.newButton("NAJDI ZAMEK",najdi());
        controlV.newComboBox("ITERATION",typIteraci());
        return controlV;
    }

    private EventHandler<ActionEvent> typIteraci() {
        return actionEvent -> {
            typProhl = ((ComboBox<eTypProhl>)actionEvent.getSource()).getValue();
            obnovList();
        };
    }

    private EventHandler<ActionEvent> nastavPred() {
        return actionEvent -> {
            langsListView.getSelectionModel().select(langsListView.getSelectionModel().getSelectedIndex()-1);
        };
    }

    private EventHandler<ActionEvent> najdi() {
        return actionEvent -> {
            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("NAJDI ZAMEK");
                dialog.setHeaderText("Zadejte nazev zamku");
                dialog.setContentText("Nazev:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String klic = result.get();
                    Zamek zamek = pamatky.najdiZamek(klic);
                    langsListView.getSelectionModel().select(zamek);
                    langsListView.scrollTo(zamek);
                }
            }catch (BinStromException e){
                alert(e.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> najdiNejblizsi() {
        return actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("NAJDI NEJBLIZSI ZAMEK");
            dialog.setHeaderText("Zadejte GPS(Jako v tabuli)");
            dialog.setContentText("GPS:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                Zamek zamek = pamatky.najdiNejbliz(result.get());
                if (zamek != null) {
                    langsListView.getSelectionModel().select(zamek);
                    langsListView.scrollTo(zamek);
                } else alert("bad coordinates input");
            }
        };
    }


    private graphic.ControlPanelHBox commandsH() {
        var controlH = new graphic.ControlPanelHBox();
        controlH.newLabel("");
        controlH.newButton("IMPORT", nacist());
        controlH.newButton("CLEAR ALL", zrusit());

        controlH.newButton("Novy Zamek",novyA());
        controlH.newButton("PREBUDUJ", prebuduj());
        controlH.newLabel("TYP KLICU: ");
        field = controlH.newField(pamatky.getActualKlic().name());
//            controlH.newComboBoxPozice("position",pozice());
        return controlH;
    }


    private EventHandler<ActionEvent> prebuduj() {
        return actionEvent -> {
            if(pamatky.getActualKlic()== GPS){
                pamatky.nastavKlic(NAZEV);
            } else pamatky.nastavKlic(GPS);
            try {
                pamatky.prebuduj();
            } catch (BinStromException e) {
                throw new RuntimeException(e);
            }
            field.setText(pamatky.getActualKlic().name());
            obnovList();

        };
    }


    @Override
    public void start(Stage stage) throws Exception {
        pamatky = new Pamatky();
        typProhl = eTypProhl.HLOUBKA;
        FlowPane root = new FlowPane(creatList(),comandsV(),commandsH());
        Scene scene = new Scene(root, 620,590);
        stage.setScene(scene);
        stage.setMaxWidth(640);
        stage.setMaxHeight(620);
        stage.setResizable(false);
        stage.setTitle("Zamky");
        stage.show();
    }


    private EventHandler<ActionEvent> novyA() {
        return event ->{
            try {
                ProcesDialog procesDialog = new ProcesDialog(new Zamek(0, "", null, pamatky.getActualKlic()));
                procesDialog.showAndWait();
                Zamek zamek = procesDialog.vratit();
                procesDialog.close();
                if (zamek != null) {
                    pamatky.vlozZamek(zamek);
                    obnovList();
                    System.out.println(zamek);
                }
            } catch (BinStromException e) {
                alert(e.getMessage());
            }

        };
    }

    private void obnovList() {
        Iterator<Zamek> iterator = pamatky.vytvorIterator(eTypProhl.HLOUBKA);
        langsListView.getItems().clear();
        if (typProhl == eTypProhl.SIRKA) {
            iterator = pamatky.vytvorIterator(eTypProhl.SIRKA);
        }
        while (iterator.hasNext()) {
            langsListView.getItems().add(iterator.next());
        }
    }

    private EventHandler<ActionEvent> zrusit() {
        return event ->{
            langsListView.getSelectionModel().select(null);
            pamatky.zrus();
            langsListView.getItems().clear();
        };

    }

    private EventHandler<ActionEvent> nacist() {
        return actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("File");
            dialog.setHeaderText("Zadejte nazev souboru");
            dialog.setContentText("SouborName:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int i = 0;
                try {
                    i = pamatky.importDatZTXT(result.get());
                } catch (BinStromException e) {
                    alert(e.getMessage());
                }
                if(i>0)obnovList();
                else new Alert(Alert.AlertType.INFORMATION,"Chyba cteni dat",ButtonType.OK).show();
            }
        };

    }


    private EventHandler<ActionEvent> vymaz() {
        return event -> {
            try {
                switch (pamatky.getActualKlic()) {
                    case NAZEV -> pamatky.odeberZamek(langsListView.getSelectionModel().getSelectedItem().getNazev());
                    case GPS -> pamatky.odeberZamek(langsListView.getSelectionModel().getSelectedItem().getGps().toString());
                }
                obnovList();
            }catch (BinStromException e) {alert(e.getMessage());}
        };
    }

    private EventHandler<ActionEvent>   nastavPosledni() {
        return event -> {
            langsListView.getSelectionModel().selectLast();
            langsListView.scrollTo(langsListView.getSelectionModel().getSelectedItem());
        };
    }

    private EventHandler<ActionEvent> nastavDalsi() {
        return event ->{
            langsListView.getSelectionModel().selectNext();
        };
    }


    private EventHandler<ActionEvent> nastavPrvni() {
        return event -> {
            langsListView.getSelectionModel().selectFirst();
            langsListView.scrollTo(langsListView.getSelectionModel().getSelectedItem());
        };
    }


    private ListView<Zamek> creatList(){
        ObservableList<Zamek> langs = FXCollections.observableArrayList();
        langsListView = new ListView<>(langs);
        langsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        langsListView.setPrefSize(500, 500);
        return langsListView;
    }

    private void alert(String msg){
        new Alert(Alert.AlertType.INFORMATION,msg,ButtonType.OK).show();
    }

}
