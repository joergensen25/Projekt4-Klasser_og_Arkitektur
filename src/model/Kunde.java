package model;

import java.util.ArrayList;

public class Kunde {
    private final String navn;
    private final String mobil;
    private final ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Kunde(String navn, String mobil) {
        this.navn = navn;
        this.mobil = mobil;

    }

    public String getNavn() {
        return navn;
    }

    public String getMobil() {
        return mobil;
    }

    public ArrayList<Bestilling> getBestillinger() {
        return new ArrayList<>(bestillinger);
    }

    void addBestilling(Bestilling bestilling) {
        if (!bestillinger.contains(bestilling)) {
            bestillinger.add(bestilling);
            bestilling.setKunde(this);
        }
    }

    void removeBestilling(Bestilling bestilling) {
        if (bestillinger.contains(bestilling)) {
            bestillinger.remove(bestilling);
            bestilling.setKunde(null);
        }
    }

    @Override
    public String toString() {
        return "Kunde: " + navn +
                ", (tlf. " + mobil +
                ")";
    }
}

