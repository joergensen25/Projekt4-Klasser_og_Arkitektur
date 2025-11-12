package gui;

import controller.Controller;
import javafx.application.Application;
import model.*;
import storage.Storage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initStorage();
        testPrint();
        Application.launch(Gui.class);

    }

    public static void testPrint() {
        System.out.println("Antal pladser: " + Storage.getPladser().size());
        for (Plads plads : Storage.getPladser()) {
            System.out.println(plads);
        }

        System.out.println();
        for (Forestilling forestilling : Storage.getForestillinger()) {
            System.out.println(forestilling);

        }

        System.out.println();
        for (Kunde kunde : Storage.getKunder()) {
            System.out.println(kunde);
        }

        System.out.println();
        for (Bestilling bestilling : Storage.getBestillinger()) {
            System.out.println(bestilling);
        }
    }

    public static void initStorage() {
        Forestilling evita = Controller.createForestilling("Evita", LocalDate.of(2023, 8, 10),
                LocalDate.of(2023, 8, 20));

        Forestilling lykkePer = Controller.createForestilling("Lykke Per", LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 10));

        Forestilling chess = Controller.createForestilling("Chess", LocalDate.of(2023, 8, 21),
                LocalDate.of(2023, 8, 30));

        Kunde andersHansen = Controller.createKunde("Anders Hansen", "11223344");
        Kunde peterJensen = Controller.createKunde("Peter Jensen", "12345678");
        Kunde nielsMadsen = Controller.createKunde("Niels Madsen", "12341234");

        for (int række = 1; række <= 15; række++) {
            for (int nr = 1; nr <= 20; nr++) {

                int pris;
                PladsType type = PladsType.STANDARD;

                // fra række 1-5:
                if (række <= 5) {
                    if (nr <= 2 || nr >= 19) {
                        pris = 450;
                    } else {
                        pris = 500;
                    }

                    // fra række 6-10:
                } else if (række <= 10) {
                    if (nr <= 2 || nr >= 19) {
                        pris = 400;
                    } else {
                        pris = 450;
                    }

                    // fra række 11-20 (koster alle 400):
                } else {
                    pris = 400;
                }

                if (række == 10 && 8 <= nr && nr <= 12) {
                    type = PladsType.KØRESTOL;
                } else if (række == 11 && 8 <= nr && nr <= 12) {
                    type = PladsType.EKSTRABEN;
                }

                Controller.createPlads(række, nr, pris, type);


            }
        }
    }

}
