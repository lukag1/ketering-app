package com.example.ketering.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.ketering.model.Korisnik;
import com.example.ketering.model.Narudzbina;
import com.example.ketering.model.StavkaNarudzbine;
import com.example.ketering.util.DatabaseConnection;

public class NarudzbinaDAO {

    public boolean saveKompletnaNarudzbina(Narudzbina narudzbina, List<StavkaNarudzbine> stavke) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 

            
            int narudzbinaId = saveNarudzbina(conn, narudzbina);
            if (narudzbinaId == -1) {
                throw new SQLException("Čuvanje narudžbine nije uspelo, ID nije generisan.");
            }

            
            for (StavkaNarudzbine stavka : stavke) {
                stavka.setNarudzbinaId(narudzbinaId);
                saveStavkaNarudzbine(conn, stavka);
            }

            conn.commit(); 
            return true;

        } catch (SQLException e) {
            
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    
                }
            }
        }
    }

    
    private int saveNarudzbina(Connection conn, Narudzbina narudzbina) throws SQLException {
        
        String sql = "INSERT INTO narudzbine (korisnik_id, datum, ukupna_cena, adresa_dostave, status) VALUES (?, ?, ?, ?, ?)";
        int narudzbinaId = -1;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, narudzbina.getKorisnikId());
            pstmt.setTimestamp(2, narudzbina.getDatum());
            
            pstmt.setBigDecimal(3, narudzbina.getUkupnaCena());
            pstmt.setString(4, narudzbina.getAdresaDostave());
            pstmt.setString(5, narudzbina.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        narudzbinaId = rs.getInt(1);
                    }
                }
            }
        }
        return narudzbinaId;
    }

    
    private void saveStavkaNarudzbine(Connection conn, StavkaNarudzbine stavka) throws SQLException {
        
        String sql = "INSERT INTO stavke_narudzbine (narudzbina_id, proizvod_id, kolicina, cena_po_komadu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, stavka.getNarudzbinaId());
            pstmt.setInt(2, stavka.getProizvodId());
            pstmt.setInt(3, stavka.getKolicina());
            
            pstmt.setBigDecimal(4, stavka.getCenaPoKomadu());

            pstmt.executeUpdate();
        }
    }

    public List<Narudzbina> getNarudzbineByKorisnikId(int korisnikId) throws SQLException {
        List<Narudzbina> narudzbine = new ArrayList<>();
        String sql = "SELECT id, korisnik_id, datum, ukupna_cena, adresa_dostave, status FROM narudzbine WHERE korisnik_id = ? ORDER BY datum DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, korisnikId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Narudzbina narudzbina = new Narudzbina();
                narudzbina.setId(rs.getInt("id"));
                narudzbina.setKorisnikId(rs.getInt("korisnik_id"));
                narudzbina.setDatum(rs.getTimestamp("datum"));
                narudzbina.setUkupnaCena(rs.getBigDecimal("ukupna_cena"));
                narudzbina.setAdresaDostave(rs.getString("adresa_dostave"));
                narudzbina.setStatus(rs.getString("status"));
                narudzbine.add(narudzbina);
            }
        } catch (SQLException e) {
            
            throw e;
        }
        return narudzbine;
    }

    

    
    public List<Narudzbina> getAllNarudzbine() throws SQLException {
        List<Narudzbina> narudzbine = new ArrayList<>();
        
        String sql = "SELECT n.*, k.ime, k.prezime FROM narudzbine n JOIN korisnici k ON n.korisnik_id = k.id ORDER BY n.datum DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Narudzbina narudzbina = new Narudzbina();
                narudzbina.setId(rs.getInt("id"));
                narudzbina.setKorisnikId(rs.getInt("korisnik_id"));
                narudzbina.setDatum(rs.getTimestamp("datum"));
                narudzbina.setUkupnaCena(rs.getBigDecimal("ukupna_cena"));
                narudzbina.setStatus(rs.getString("status"));
                narudzbina.setAdresaDostave(rs.getString("adresa_dostave"));

                
                Korisnik korisnik = new Korisnik();
                korisnik.setId(rs.getInt("korisnik_id"));
                korisnik.setIme(rs.getString("ime"));
                korisnik.setPrezime(rs.getString("prezime"));
                
                
                narudzbina.setKorisnik(korisnik);

                narudzbine.add(narudzbina);
            }
        }
        return narudzbine;
    }

    
    public boolean updateStatusNarudzbine(int narudzbinaId, String status) throws SQLException {
        String sql = "UPDATE narudzbine SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, narudzbinaId);
            return ps.executeUpdate() > 0;
        }
    }

    
    public boolean deleteNarudzbina(int narudzbinaId) throws SQLException {
        String sql = "DELETE FROM narudzbine WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, narudzbinaId);
            return ps.executeUpdate() > 0;
        }
    }

    

    
    public java.math.BigDecimal getUkupanPromet() throws SQLException {
        String sql = "SELECT SUM(ukupna_cena) AS total FROM narudzbine WHERE status != 'CANCELLED'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        }
        return java.math.BigDecimal.ZERO;
    }
}
