package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException; 
import java.util.HashMap;
import java.util.Map;

import com.example.ketering.dao.ProizvodDAO;
import com.example.ketering.model.Proizvod;
import com.example.ketering.model.StavkaKorpe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/korpa")
public class KorpaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProizvodDAO proizvodDAO;

    @Override
    public void init() {
        proizvodDAO = new ProizvodDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String akcija = request.getParameter("akcija");

        try {
            if ("dodaj".equals(akcija)) {
                dodajUKorpu(request, response);
            } else if ("ukloni".equals(akcija)) {
                ukloniIzKorpe(request, response);
            }
            
        } catch (SQLException e) {
            
            throw new ServletException("Greška pri radu sa bazom podataka u korpi.", e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.getRequestDispatcher("/korpa.jsp").forward(request, response);
    }

    
    private void dodajUKorpu(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        
        Map<Integer, StavkaKorpe> korpa = (Map<Integer, StavkaKorpe>) session.getAttribute("korpa");
        if (korpa == null) {
            korpa = new HashMap<>();
        }

        try {
            int proizvodId = Integer.parseInt(request.getParameter("proizvodId"));
            
            StavkaKorpe postojecaStavka = korpa.get(proizvodId);

            if (postojecaStavka != null) {
                postojecaStavka.setKolicina(postojecaStavka.getKolicina() + 1);
            } else {
                
                Proizvod proizvod = proizvodDAO.getProizvodById(proizvodId);
                if (proizvod != null) {
                    korpa.put(proizvodId, new StavkaKorpe(proizvod, 1));
                }
            }
            
            session.setAttribute("korpa", korpa);

        } catch (NumberFormatException e) {
            
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/meni");
    }

    private void ukloniIzKorpe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<Integer, StavkaKorpe> korpa = (Map<Integer, StavkaKorpe>) session.getAttribute("korpa");

        if (korpa != null) {
            try {
                int proizvodId = Integer.parseInt(request.getParameter("proizvodId"));
                korpa.remove(proizvodId);
                session.setAttribute("korpa", korpa);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/korpa");
    }
}

