package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.ketering.dao.NarudzbinaDAO;
import com.example.ketering.model.Korisnik;
import com.example.ketering.model.Narudzbina;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
    private NarudzbinaDAO narudzbinaDAO;

    @Override
    public void init() {
        narudzbinaDAO = new NarudzbinaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Korisnik korisnik = (session != null) ? (Korisnik) session.getAttribute("korisnik") : null;

        if (korisnik == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Narudzbina> listaNarudzbina = narudzbinaDAO.getNarudzbineByKorisnikId(korisnik.getId());
            request.setAttribute("narudzbine", listaNarudzbina);
            request.getRequestDispatcher("/profil.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Greška pri dohvatanju narudžbina.", e);
        }
    }
}