package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.ketering.dao.ProizvodDAO;
import com.example.ketering.model.Proizvod;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/meni")
public class MeniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProizvodDAO proizvodDAO;

    @Override
    public void init() throws ServletException {
        proizvodDAO = new ProizvodDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Proizvod> listaProizvoda = proizvodDAO.getSviProizvodi();

            request.setAttribute("proizvodi", listaProizvoda);

            request.getRequestDispatcher("/meni.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Greška prilikom dohvatanja proizvoda iz baze.", e);
        }
    }
}
