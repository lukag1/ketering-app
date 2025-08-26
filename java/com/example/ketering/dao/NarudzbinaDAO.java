package com.example.ketering.dao;

import java.sql.Connection; // DODATO: Import koji nedostaje
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

    /**
     * Čuva kompletnu narudžbinu (glavni zapis i sve stavke) unutar jedne transakcije.
     * @param narudzbina Objekat Narudzbina.
     * @param stavke Lista objekata StavkaNarudzbine.
     * @return true ako je transakcija uspešna, false u suprotnom.
     */
    public boolean saveKompletnaNarudzbina(Narudzbina narudzbina, List<StavkaNarudzbine> stavke) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Započinjemo transakciju

            // 1. Čuvanje narudžbine i dobijanje ID-a
            int narudzbinaId = saveNarudzbina(conn, narudzbina);
            if (narudzbinaId == -1) {
                throw new SQLException("Čuvanje narudžbine nije uspelo, ID nije generisan.");
            }

            // 2. Čuvanje svih stavki narudžbine
            for (StavkaNarudzbine stavka : stavke) {
                stavka.setNarudzbinaId(narudzbinaId);
                saveStavkaNarudzbine(conn, stavka);
            }

            conn.commit(); // Potvrđujemo transakciju
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Poništavamo sve promene u slučaju greške
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Pomoćna metoda za čuvanje narudžbine. Učestvuje u transakciji.
     */
    private int saveNarudzbina(Connection conn, Narudzbina narudzbina) throws SQLException {
        // ISPRAVLJEN SQL: Dodata adresa_dostave
        String sql = "INSERT INTO narudzbine (korisnik_id, datum, ukupna_cena, adresa_dostave, status) VALUES (?, ?, ?, ?, ?)";
        int narudzbinaId = -1;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, narudzbina.getKorisnikId());
            pstmt.setTimestamp(2, narudzbina.getDatum());
            // ISPRAVKA: Koristi se setBigDecimal umesto setDouble
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

    /**
     * Pomoćna metoda za čuvanje stavke narudžbine. Učestvuje u transakciji.
     */
    private void saveStavkaNarudzbine(Connection conn, StavkaNarudzbine stavka) throws SQLException {
        // ISPRAVLJEN SQL: Koristi se 'cena_po_komadu' kako je definisano u bazi
        String sql = "INSERT INTO stavke_narudzbine (narudzbina_id, proizvod_id, kolicina, cena_po_komadu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, stavka.getNarudzbinaId());
            pstmt.setInt(2, stavka.getProizvodId());
            pstmt.setInt(3, stavka.getKolicina());
            // ISPRAVKA: Koristi se setBigDecimal (pretpostavljajući da i StavkaNarudzbine koristi BigDecimal)
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
            e.printStackTrace();
            throw e;
        }
        return narudzbine;
    }

    // --- NOVE METODE ZA ADMINA ---

    /**
     * Dohvata SVE narudžbine od SVIH korisnika za admin panel.
     */
    public List<Narudzbina> getAllNarudzbine() throws SQLException {
        List<Narudzbina> narudzbine = new ArrayList<>();
        // Upit spaja narudžbine sa korisnicima da bismo imali ime i prezime naručioca
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
                narudzbina.setAdresaDostave(rs.getString("adresa_dostave"));
                narudzbina.setStatus(rs.getString("status"));
                
                // Kreiramo privremeni Korisnik objekat samo za prikaz imena
                Korisnik k = new Korisnik();
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                narudzbina.setKorisnik(k); // Postavljamo korisnika u narudžbinu

                narudzbine.add(narudzbina);
            }
        }
        return narudzbine;
    }

    /**
     * Ažurira status postojeće narudžbine.
     */
    public boolean updateStatusNarudzbine(int narudzbinaId, String status) throws SQLException {
        String sql = "UPDATE narudzbine SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, narudzbinaId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Briše narudžbinu iz baze.
     */
    public boolean deleteNarudzbina(int narudzbinaId) throws SQLException {
        String sql = "DELETE FROM narudzbine WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, narudzbinaId);
            return ps.executeUpdate() > 0;
        }
    }

    // --- METODE ZA IZVEŠTAJE ---

    /**
     * Računa ukupan promet od svih narudžbina koje nisu otkazane.
     * @return Ukupan promet kao BigDecimal.
     */
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
