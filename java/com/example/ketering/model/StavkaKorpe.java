package com.example.ketering.model;

public class StavkaKorpe {
    private Proizvod proizvod;
    private int kolicina;

    public StavkaKorpe(Proizvod proizvod, int kolicina) {
        this.proizvod = proizvod;
        this.kolicina = kolicina;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getUkupnaCena() {
        return proizvod.getCena() * kolicina;
    }
}
