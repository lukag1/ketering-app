package com.example.ketering.model;

public class Korisnik {
    private int id;
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    // ISPRAVKA: Polje 'uloga' je sada tipa Uloga, a ne String.
    private Uloga uloga; 

    // Konstruktori
    public Korisnik() {
    }

    public Korisnik(int id, String ime, String prezime, String email, String lozinka, Uloga uloga) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.uloga = uloga;
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
