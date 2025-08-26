package com.example.ketering.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Narudzbina {
    private int id;
    private int korisnikId;
    private Timestamp datum; // Koristimo 'datum' kako se zove u vašoj tabeli
    private BigDecimal ukupnaCena;
    private String adresaDostave;
    private String status;
    private List<StavkaNarudzbine> stavke;
    
    // DODATO: Polje za čuvanje podataka o korisniku koji je naručio
    private Korisnik korisnik;

    // Konstruktori
    public Narudzbina() {
    }

    public Narudzbina(int id, int korisnikId, Timestamp datum, BigDecimal ukupnaCena, String adresaDostave, String status) {
        this.id = id;
        this.korisnikId = korisnikId;
        this.datum = datum;
        this.ukupnaCena = ukupnaCena;
        this.adresaDostave = adresaDostave;
        this.status = status;
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public String getAdresaDostave() {
        return adresaDostave;
    }

    public void setAdresaDostave(String adresaDostave) {
        this.adresaDostave = adresaDostave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StavkaNarudzbine> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaNarudzbine> stavke) {
        this.stavke = stavke;
    }

    // DODATO: Getter i Setter za korisnika
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
