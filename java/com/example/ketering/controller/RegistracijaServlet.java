package com.example.ketering.controller;

import com.example.ketering.dao.KorisnikDAO;
import com.example.ketering.model.Korisnik;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registracija")
public class RegistracijaServlet extends HttpServlet {
    private KorisnikDAO korisnikDAO;

    @Override
    public void init() {
        korisnikDAO = new KorisnikDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Prosleđuje na JSP stranicu za prikaz forme
        req.getRequestDispatcher("/registracija.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String email = request.getParameter("email");
        String lozinka = request.getParameter("lozinka");
        String potvrdaLozinke = request.getParameter("potvrdaLozinke");

        // Validacija
        if (!lozinka.equals(potvrdaLozinke)) {
            request.setAttribute("greska", "Lozinke se ne poklapaju.");
            request.getRequestDispatcher("/registracija.jsp").forward(request, response);
            return;
        }

        try {
            if (korisnikDAO.getKorisnikByEmail(email) != null) {
                request.setAttribute("greska", "Korisnik sa unetim emailom već postoji.");
                request.getRequestDispatcher("/registracija.jsp").forward(request, response);
                return;
            }

            Korisnik noviKorisnik = new Korisnik();
            noviKorisnik.setIme(ime);
            noviKorisnik.setPrezime(prezime);
            noviKorisnik.setEmail(email);
            noviKorisnik.setLozinka(lozinka); // U realnoj aplikaciji, ovde bi se lozinka heširala

            korisnikDAO.addKorisnik(noviKorisnik);

            request.setAttribute("poruka", "Uspešno ste se registrovali! Možete se prijaviti.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Greška pri radu sa bazom tokom registracije.", e);
        }
    }
}
