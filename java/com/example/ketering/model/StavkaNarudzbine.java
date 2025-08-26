package com.example.ketering.model;

import java.math.BigDecimal;

public class StavkaNarudzbine {

    private int id;
    private int narudzbinaId;
    private int proizvodId;
    private int kolicina;
    private BigDecimal cenaPoKomadu;

    // Opciono, možemo čuvati i ceo objekat Proizvod radi lakšeg prikaza
    private Proizvod proizvod;

    // Konstruktori
    public StavkaNarudzbine() {}

    public StavkaNarudzbine(Proizvod proizvod, int kolicina) {
        this.proizvod = proizvod;
        this.proizvodId = proizvod.getId();
        this.kolicina = kolicina;
        // Cena se postavlja na trenutnu cenu proizvoda
        this.cenaPoKomadu = BigDecimal.valueOf(proizvod.getCena()); 
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNarudzbinaId() {
        return narudzbinaId;
    }

    public void setNarudzbinaId(int narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public int getProizvodId() {
        return proizvodId;
    }

    public void setProizvodId(int proizvodId) {
        this.proizvodId = proizvodId;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getCenaPoKomadu() {
        return cenaPoKomadu;
    }

    public void setCenaPoKomadu(BigDecimal cenaPoKomadu) {
        this.cenaPoKomadu = cenaPoKomadu;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }
}
