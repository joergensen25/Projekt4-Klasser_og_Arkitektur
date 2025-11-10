package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forestilling {
    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private ArrayList<Bestilling> bestillinger = new ArrayList<>();

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

    public Bestilling createBestilling(LocalDate dato, Kunde kunde) {
        Bestilling bestilling = new Bestilling(dato, this, kunde);
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
        return "Forestilling: " +
                "navn: '" + navn + '\'' +
                ", Spiller fra: " + startDato +
                ", Indtil: " + slutDato +
                ", bestillinger: " + bestillinger;
    }
}
