package com.example.ketering.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ketering.model.Proizvod;
import com.example.ketering.util.DatabaseConnection;

public class ProizvodDAO {

    /**
     * Dohvata sve proizvode iz baze podataka.
     * @return Lista svih proizvoda.
     */
    public List<Proizvod> getSviProizvodi() throws SQLException {
        List<Proizvod> listaProizvoda = new ArrayList<>();
        String sql = "SELECT * FROM proizvodi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proizvod proizvod = mapRowToProizvod(rs);
                listaProizvoda.add(proizvod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return listaProizvoda;
    }

    /**
     * Dohvata jedan proizvod iz baze podataka na osnovu njegovog ID-a.
     * @param id ID proizvoda koji se traži.
     * @return Proizvod objekat ako je pronađen, u suprotnom null.
     */
    public Proizvod getProizvodById(int id) throws SQLException {
        Proizvod proizvod = null;
        String sql = "SELECT * FROM proizvodi WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proizvod = mapRowToProizvod(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return proizvod;
    }

    /**
     * Dodaje novi proizvod u bazu.
     * @param proizvod Objekat Proizvod koji treba dodati.
     */
    public void addProizvod(Proizvod proizvod) throws SQLException {
        String sql = "INSERT INTO proizvodi (naziv, opis, cena, tip) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, proizvod.getNaziv());
            ps.setString(2, proizvod.getOpis());
            ps.setDouble(3, proizvod.getCena());
            ps.setString(4, proizvod.getTip());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Ažurira postojeći proizvod u bazi.
     * @param proizvod Objekat Proizvod sa ažuriranim podacima.
     * @return true ako je ažuriranje uspešno, false inače.
     */
    public boolean updateProizvod(Proizvod proizvod) throws SQLException {
        String sql = "UPDATE proizvodi SET naziv = ?, opis = ?, cena = ?, tip = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, proizvod.getNaziv());
            ps.setString(2, proizvod.getOpis());
            ps.setDouble(3, proizvod.getCena());
            ps.setString(4, proizvod.getTip());
            ps.setInt(5, proizvod.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Briše proizvod iz baze na osnovu njegovog ID-a.
     * @param id ID proizvoda koji treba obrisati.
     * @return true ako je brisanje uspešno, false inače.
     */
    public boolean deleteProizvod(int id) throws SQLException {
        String sql = "DELETE FROM proizvodi WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Pomoćna metoda za mapiranje reda iz ResultSet-a u Proizvod objekat.
     */
    private Proizvod mapRowToProizvod(ResultSet rs) throws SQLException {
        Proizvod proizvod = new Proizvod();
        proizvod.setId(rs.getInt("id"));
        proizvod.setNaziv(rs.getString("naziv"));
        proizvod.setOpis(rs.getString("opis"));
        proizvod.setCena(rs.getDouble("cena"));
        proizvod.setTip(rs.getString("tip"));
        // UKLONJENO: proizvod.setSlikaUrl(rs.getString("slika_url"));
        return proizvod;
    }

    /**
     * Dohvata najprodavanije proizvode na osnovu broja narudžbina.
     * @param limit Broj najprodavanijih proizvoda koji se vraća.
     * @return Lista najprodavanijih proizvoda.
     */
    public List<Proizvod> getNajprodavanijiProizvodi(int limit) throws SQLException {
        List<Proizvod> proizvodi = new ArrayList<>();
        String sql = "SELECT p.*, SUM(sn.kolicina) AS ukupno_prodato " +
                     "FROM proizvodi p " +
                     "JOIN stavke_narudzbine sn ON p.id = sn.proizvod_id " +
                     "GROUP BY p.id " +
                     "ORDER BY ukupno_prodato DESC " +
                     "LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proizvod proizvod = mapRowToProizvod(rs);
                    // Možete dodati i količinu ako želite da je prikažete
                    // proizvod.setUkupnoProdato(rs.getInt("ukupno_prodato")); 
                    proizvodi.add(proizvod);
                }
            }
        }
        return proizvodi;
    }
}

