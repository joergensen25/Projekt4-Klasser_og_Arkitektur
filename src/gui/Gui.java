package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Bestilling;
import model.Forestilling;
import model.Kunde;
import model.Plads;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gui extends Application {
    public void start(Stage stage) {
        stage.setTitle("Teater bestilling");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    ListView<Forestilling> lvwForestillinger = new ListView<>();
    ListView<Kunde> lvwKunder = new ListView<>();
    ListView<Plads> lvwPladser = new ListView<>();


    TextField txfNavn = new TextField();
    DatePicker dpStart = new DatePicker();
    DatePicker dpSlut = new DatePicker();
    DatePicker dpDato= new DatePicker();

    TextField txfKundeNavn = new TextField();
    TextField txfKundeMobil = new TextField();

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // FORESTILLINGER
        Label lblForestillinger = new Label("Forestillinger");
        pane.add(lblForestillinger, 0, 0);
        pane.add(lvwForestillinger, 0, 1, 2, 1);

        Label lblNavn = new Label("Navn");
        pane.add(lblNavn, 0, 3);
        pane.add(txfNavn, 1, 3);

        Label lblStartDato = new Label("Vælg startdato");
        pane.add(lblStartDato, 0, 4);
        pane.add(dpStart, 1, 4);

        Label lblSlutDato = new Label("Vælg slutdato");
        pane.add(lblSlutDato, 0, 5);
        pane.add(dpSlut, 1, 5);

        Button btnOpretForestilling = new Button("Opret forestilling");
        pane.add(btnOpretForestilling, 1, 6);
        btnOpretForestilling.setOnAction(event -> this.forestillingAction());

        // KUNDER
        Label lblKunder = new Label("Kunder");
        pane.add(lblKunder, 2, 0);
        pane.add(lvwKunder, 2, 1, 2, 1);

        Label lblKundeNavn = new Label("Kunde navn");
        pane.add(lblKundeNavn, 2, 3);
        pane.add(txfKundeNavn, 3, 3);

        Label lblKundeMobil = new Label("Kunde mobil");
        pane.add(lblKundeMobil, 2, 4);
        pane.add(txfKundeMobil, 3, 4);

        Button btnOpretKunde = new Button("Opret kunde");
        pane.add(btnOpretKunde, 3, 5);
        btnOpretKunde.setOnAction(event -> this.kundeAction());

        // PLADSER
        Label lblPladser = new Label("Pladser");
        pane.add(lblPladser, 4, 0);
        pane.add(lvwPladser, 4, 1, 2, 1);

        Label lblDato = new Label("Dato");
        pane.add(lblDato, 4, 3);
        pane.add(dpDato, 5, 3);


        Button btnOpretBestilling = new Button("Opret bestilling");
        pane.add(btnOpretBestilling, 5, 4, 2, 1);
        btnOpretBestilling.setOnAction(event -> this.bestillingAction());
        lvwPladser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



        // TILFØJER ITEMS FRA STORAGE
        lvwForestillinger.getItems().setAll(Storage.getForestillinger());
        lvwKunder.getItems().setAll(Storage.getKunder());
        lvwPladser.getItems().setAll(Storage.getPladser());
    }

    private void bestillingAction() {

        Forestilling forestilling = lvwForestillinger.getSelectionModel().getSelectedItem();
        Kunde kunde = lvwKunder.getSelectionModel().getSelectedItem();
        LocalDate dato = dpDato.getValue();
        ArrayList<Plads> pladser = new ArrayList<>(lvwPladser.getSelectionModel().getSelectedItems());

        if (forestilling == null || kunde == null || dato == null || pladser.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Manglende oplysninger",
                    "Vælg en forestilling, kunde, dato og mindst en plads");
            return;
        }

        Bestilling bestilling = Controller.opretBestillingMedPladser(forestilling, kunde, dato, pladser);

        if (bestilling == null) {
            showAlert(Alert.AlertType.ERROR, "Bestilling mislykkedes", "Pladserne er ikke ledige, " +
                    "eller bestilling er udenfor visningsperiode");

        } else {
            showAlert(Alert.AlertType.INFORMATION, "Bestilling oprettet", "Bestillingen er oprettet for den valgte dato.");
        }




    }

    private void forestillingAction() {
        try {
            String navn = txfNavn.getText().trim();
            LocalDate start = dpStart.getValue();
            LocalDate slut = dpSlut.getValue();

            if (navn.isEmpty() || start == null || slut == null ) {
                showAlert(Alert.AlertType.WARNING, "Manglende oplysninger",
                        "Udfyld alle felter, før du fortsætter");
                return;
            }

            Forestilling forestilling = Controller.createForestilling(navn, start, slut);
            lvwForestillinger.getItems().add(forestilling);

            txfNavn.clear();
            dpStart.setValue(null);
            dpSlut.setValue(null);

            showAlert(Alert.AlertType.INFORMATION, "Forestilling oprettet", "Forestillingen '" +
                    navn + "' blev oprettet.");

        } catch (Exception e) {
           showAlert(Alert.AlertType.ERROR, "Fejl", "Der opstod en fejl." + e.getMessage());
        }
    }

    private void kundeAction() {
        String kundeNavn = txfKundeNavn.getText().trim();
        String mobil = txfKundeMobil.getText().trim();

        if (kundeNavn.isEmpty() || mobil.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Manglende oplysninger",
                    "Udfyld alle felter før du fortsætter.");
            return;
        }

        Kunde kunde = Controller.createKunde(kundeNavn, mobil);
        lvwKunder.getItems().add(kunde);

        txfKundeNavn.clear();
        txfKundeMobil.clear();

        showAlert(Alert.AlertType.INFORMATION, "Kunde oprettet",
                "Kunden '" + kundeNavn + "' blev oprettet.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}
