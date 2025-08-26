package com.example.ketering.model;

public class Proizvod {
    private int id;
    private String naziv;
    private String opis;
    private double cena;
    private String tip;

    // Konstruktori
    public Proizvod() {
    }

    public Proizvod(int id, String naziv, String opis, double cena, String tip) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.tip = tip;
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

}
