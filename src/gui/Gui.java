package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Forestilling;
import model.Kunde;

import java.time.LocalDate;

public class Gui extends Application {
    public void start(Stage stage) {
        stage.setTitle("Teater bestilling");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private final ListView<Forestilling> lvwForestillinger = new ListView<>();
    private final ListView<Kunde> lvwKunder = new ListView<>();

    private final TextField txfNavn = new TextField();
    private final TextField txfStartDato = new TextField();
    private final TextField txfSlutDato = new TextField();

    private final TextField txfKundeNavn = new TextField();
    private final TextField txfKundeMobil = new TextField();


    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblForestillinger = new Label("Forestillinger");
        pane.add(lblForestillinger, 0, 0);
        pane.add(lvwForestillinger, 0, 1, 2, 1);

        Label lblNavn = new Label("Navn");
        pane.add(lblNavn, 0, 3);
        pane.add(txfNavn, 1, 3);

        Label lblStartDato = new Label("Vælg startdato");
        pane.add(lblStartDato, 0, 4);
        final DatePicker datePicker = new DatePicker();
        pane.add(datePicker, 1, 4);

        Label lblSlutDato = new Label("Vælg slutdato");
        pane.add(lblSlutDato, 0, 5);
        DatePicker datePicker1 = new DatePicker();
        pane.add(datePicker1, 1, 5);



        Label lblKunder = new Label("Kunder");
        pane.add(lblKunder, 2, 0);
        pane.add(lvwKunder, 2, 1, 2, 1);

        Label lblKundeNavn = new Label("Kunde navn");
        pane.add(lblKundeNavn, 2, 3);
        pane.add(txfKundeNavn, 3, 3);

        Label lblKundeMobil = new Label("Kunde mobil");
        pane.add(lblKundeMobil, 2, 4);
        pane.add(txfKundeMobil, 3, 4);

        Button btnOpretForestilling = new Button("Opret forestilling");
        pane.add(btnOpretForestilling, 1, 6);

        Button btnOpretKunde = new Button("Opret kunde");
        pane.add(btnOpretKunde, 3, 5);


        btnOpretForestilling.setOnAction(event -> this.forestillingAction());
        btnOpretKunde.setOnAction(event -> this.kundeAction());


    }


    private void forestillingAction() {
        try {
            String navn = txfNavn.getText().trim();
            String startDato = txfStartDato.getText().trim();
            String slutDato = txfSlutDato.getText().trim();

            if (navn.isEmpty() || startDato.isEmpty() || slutDato.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Manglende oplysninger",
                        "Udfyld alle felter, før du fortsætter");
            }

            LocalDate start = LocalDate.parse(startDato);
            LocalDate slut = LocalDate.parse(slutDato);

            Forestilling forestilling = Controller.createForestilling(navn, start, slut);

            lvwForestillinger.getItems().add(forestilling);

            txfNavn.clear();
            txfStartDato.clear();
            txfSlutDato.clear();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Fejl i datoformat",
                    "Datoerne skal skrives som YYYY-MM-DD");

        }
    }


    private void kundeAction() {
        String kundeNavn = txfKundeNavn.getText().trim();
        String mobil = txfKundeMobil.getText().trim();


        Kunde kunde = Controller.createKunde(kundeNavn, mobil);
        lvwKunder.getItems().add(kunde);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }


}
