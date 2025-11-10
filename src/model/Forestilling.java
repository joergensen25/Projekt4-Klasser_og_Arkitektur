package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forestilling {
    private final String navn;
    private final LocalDate startDato;
    private final LocalDate slutDato;
    private final ArrayList<Bestilling> bestillinger = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Forestilling: '" + navn +
                "' (" + startDato + " - " + slutDato +
                "). bestillinger: " + bestillinger;
    }
}
