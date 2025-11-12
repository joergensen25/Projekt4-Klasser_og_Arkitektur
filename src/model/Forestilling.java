package model;

import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forestilling {
    private final String navn;
    private final LocalDate startDato;
    private final LocalDate slutDato;
    private final ArrayList<Bestilling> bestillinger = new ArrayList<>();
    private final ArrayList<Forestilling> forestillinger = new ArrayList<>();

    public Forestilling(String navn, LocalDate startDato, LocalDate slutDato) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public ArrayList<Bestilling> getBestillinger() {
        return new ArrayList<>(bestillinger);
    }

    public Bestilling createBestilling(LocalDate dato, Forestilling forestilling, Kunde kunde) {
        Bestilling bestilling = new Bestilling(dato, forestilling, kunde);
        bestillinger.add(bestilling);
        return bestilling;
    }


    void addBestilling(Bestilling bestilling) {
        if (!bestillinger.contains(bestilling)) {
            bestillinger.add(bestilling);
        }
    }

    public boolean erPladsLedig(int række, int nr, LocalDate dato) {

        for (Bestilling bestilling : bestillinger) { // for each med alle bestillinger
            if (bestilling.getDato().equals(dato)) { // hvis ønsket dato allerede findes
                for (Plads plads : bestilling.getPladser()) { // for each med alle pladser
                    if (plads.getNr() == nr && plads.getRække() == række) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Forestilling: '" + navn +
                "' (" + startDato + " - " + slutDato +
                ")";
    }
}
