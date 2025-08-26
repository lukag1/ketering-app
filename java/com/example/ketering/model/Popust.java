package com.example.ketering.model;

public class Popust {
    private int id;
    private String opis;
    private double procenat;
    private int uslovMinimalnaKolicina;
    private double uslovMinimalnaCena;

    // Konstruktori
    public Popust() {
    }

    public Popust(int id, String opis, double procenat, int uslovMinimalnaKolicina, double uslovMinimalnaCena) {
        this.id = id;
        this.opis = opis;
        this.procenat = procenat;
        this.uslovMinimalnaKolicina = uslovMinimalnaKolicina;
        this.uslovMinimalnaCena = uslovMinimalnaCena;
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getProcenat() {
        return procenat;
    }

    public void setProcenat(double procenat) {
        this.procenat = procenat;
    }

    public int getUslovMinimalnaKolicina() {
        return uslovMinimalnaKolicina;
    }

    public void setUslovMinimalnaKolicina(int uslovMinimalnaKolicina) {
        this.uslovMinimalnaKolicina = uslovMinimalnaKolicina;
    }

    public double getUslovMinimalnaCena() {
        return uslovMinimalnaCena;
    }

    public void setUslovMinimalnaCena(double uslovMinimalnaCena) {
        this.uslovMinimalnaCena = uslovMinimalnaCena;
    }
}
