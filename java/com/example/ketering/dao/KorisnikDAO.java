package com.example.ketering.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ketering.model.Korisnik;
import com.example.ketering.model.Uloga; 
import com.example.ketering.util.DatabaseConnection;

public class KorisnikDAO {

    public Korisnik validate(String email, String lozinka) throws SQLException {
        Korisnik korisnik = null;
        String sql = "SELECT k.id, k.ime, k.prezime, k.email, k.lozinka, u.id as uloga_id, u.naziv as uloga_naziv FROM korisnici k JOIN uloge u ON k.uloga_id = u.id WHERE k.email = ? AND k.lozinka = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, lozinka);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    korisnik = mapRowToKorisnik(rs);
                }
            }
        }
        return korisnik;
    }

    public Korisnik getKorisnikByEmail(String email) throws SQLException {
        Korisnik korisnik = null;
        
        String sql = "SELECT k.id, k.ime, k.prezime, k.email, k.lozinka, u.id as uloga_id, u.naziv as uloga_naziv FROM korisnici k JOIN uloge u ON k.uloga_id = u.id WHERE k.email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                korisnik = mapRowToKorisnik(rs);
            }
        }
        return korisnik;
    }

    
    public void addKorisnik(Korisnik korisnik) throws SQLException {
        
        String sql = "INSERT INTO korisnici (ime, prezime, email, lozinka, uloga_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, korisnik.getIme());
            ps.setString(2, korisnik.getPrezime());
            ps.setString(3, korisnik.getEmail());
            ps.setString(4, korisnik.getLozinka());

            
            
            
            if (korisnik.getUloga() != null && korisnik.getUloga().getId() > 0) {
                ps.setInt(5, korisnik.getUloga().getId());
            } else {
                ps.setInt(5, 1); 
            }
            ps.executeUpdate();
        }
    }

    public List<Korisnik> getAllKorisnici() throws SQLException {
        List<Korisnik> korisnici = new ArrayList<>();
        String sql = "SELECT k.id, k.ime, k.prezime, k.email, k.lozinka, u.id as uloga_id, u.naziv as uloga_naziv FROM korisnici k JOIN uloge u ON k.uloga_id = u.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                korisnici.add(mapRowToKorisnik(rs));
            }
        }
        return korisnici;
    }

    public Korisnik getKorisnikById(int id) throws SQLException {
        Korisnik korisnik = null;
        String sql = "SELECT k.id, k.ime, k.prezime, k.email, k.lozinka, u.id as uloga_id, u.naziv as uloga_naziv FROM korisnici k JOIN uloge u ON k.uloga_id = u.id WHERE k.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    korisnik = mapRowToKorisnik(rs);
                }
            }
        }
        return korisnik;
    }

    public boolean updateKorisnik(Korisnik korisnik) throws SQLException {
        String sql = "UPDATE korisnici SET ime = ?, prezime = ?, email = ?, lozinka = ?, uloga_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, korisnik.getIme());
            pstmt.setString(2, korisnik.getPrezime());
            pstmt.setString(3, korisnik.getEmail());
            pstmt.setString(4, korisnik.getLozinka());
            pstmt.setInt(5, korisnik.getUloga().getId());
            pstmt.setInt(6, korisnik.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteKorisnik(int id) throws SQLException {
        String sql = "DELETE FROM korisnici WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Uloga> getAllUloge() throws SQLException {
        List<Uloga> uloge = new ArrayList<>();
        String sql = "SELECT id, naziv FROM uloge";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Uloga uloga = new Uloga();
                uloga.setId(rs.getInt("id"));
                uloga.setNaziv(rs.getString("naziv"));
                uloge.add(uloga);
            }
        }
        return uloge;
    }

    
    public Korisnik getKorisnikByEmailAndLozinka(String email, String lozinka) throws SQLException {
        Korisnik korisnik = null;
        String sql = "SELECT k.*, u.naziv AS naziv_uloge FROM korisnici k JOIN uloge u ON k.uloga_id = u.id WHERE k.email = ? AND k.lozinka = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                korisnik = new Korisnik();
                korisnik.setId(rs.getInt("id"));
                korisnik.setIme(rs.getString("ime"));
                korisnik.setPrezime(rs.getString("prezime"));
                korisnik.setEmail(rs.getString("email"));
                korisnik.setLozinka(rs.getString("lozinka"));

                Uloga uloga = new Uloga();
                uloga.setId(rs.getInt("uloga_id"));
                uloga.setNaziv(rs.getString("naziv_uloge"));
                korisnik.setUloga(uloga);
            }
        }
        return korisnik;
    }

    
    private Korisnik mapRowToKorisnik(ResultSet rs) throws SQLException {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(rs.getInt("id"));
        korisnik.setIme(rs.getString("ime"));
        korisnik.setPrezime(rs.getString("prezime"));
        korisnik.setEmail(rs.getString("email"));
        korisnik.setLozinka(rs.getString("lozinka"));

        Uloga uloga = new Uloga();
        uloga.setId(rs.getInt("uloga_id"));
        uloga.setNaziv(rs.getString("uloga_naziv"));
        korisnik.setUloga(uloga);
        
        return korisnik;
    }
}