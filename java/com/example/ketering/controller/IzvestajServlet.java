package com.example.ketering.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.example.ketering.dao.NarudzbinaDAO;
import com.example.ketering.dao.ProizvodDAO;
import com.example.ketering.model.Narudzbina;
import com.example.ketering.model.Proizvod;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/izvestaji")
public class IzvestajServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NarudzbinaDAO narudzbinaDAO;
    private ProizvodDAO proizvodDAO;

    public void init() {
        narudzbinaDAO = new NarudzbinaDAO();
        proizvodDAO = new ProizvodDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Dohvatanje svih narudžbina za prikaz
            List<Narudzbina> sveNarudzbine = narudzbinaDAO.getAllNarudzbine();
            request.setAttribute("sveNarudzbine", sveNarudzbine);

            // Dohvatanje ukupnog prometa
            BigDecimal ukupanPromet = narudzbinaDAO.getUkupanPromet();
            request.setAttribute("ukupanPromet", ukupanPromet);

            // Dohvatanje najprodavanijih proizvoda (npr. top 5)
            List<Proizvod> najprodavaniji = proizvodDAO.getNajprodavanijiProizvodi(5);
            request.setAttribute("najprodavanijiProizvodi", najprodavaniji);

            request.getRequestDispatcher("/izvestaji.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Greška pri generisanju izveštaja", e);
        }
    }
}
