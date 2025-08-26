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

@WebServlet("/admin/proizvodi")
public class ProizvodServlet extends HttpServlet {
    private ProizvodDAO proizvodDAO;

    @Override
    public void init() {
        proizvodDAO = new ProizvodDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertProizvod(request, response);
                    break;
                case "delete":
                    deleteProizvod(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateProizvod(request, response);
                    break;
                default:
                    listProizvodi(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProizvodi(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Proizvod> listaProizvoda = proizvodDAO.getSviProizvodi();
        request.setAttribute("listaProizvoda", listaProizvoda);
        request.getRequestDispatcher("/admin/proizvodi-lista.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/proizvod-forma.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Proizvod postojeciProizvod = proizvodDAO.getProizvodById(id);
        request.setAttribute("proizvod", postojeciProizvod);
        request.getRequestDispatcher("/admin/proizvod-forma.jsp").forward(request, response);
    }

    private void insertProizvod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String naziv = request.getParameter("naziv");
        String opis = request.getParameter("opis");
        double cena = Double.parseDouble(request.getParameter("cena"));
        String tip = request.getParameter("tip");

        Proizvod noviProizvod = new Proizvod();
        noviProizvod.setNaziv(naziv);
        noviProizvod.setOpis(opis);
        noviProizvod.setCena(cena);
        noviProizvod.setTip(tip);
        
        proizvodDAO.addProizvod(noviProizvod);
        response.sendRedirect(request.getContextPath() + "/admin/proizvodi");
    }

    private void updateProizvod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String naziv = request.getParameter("naziv");
        String opis = request.getParameter("opis");
        double cena = Double.parseDouble(request.getParameter("cena"));
        String tip = request.getParameter("tip");

        Proizvod proizvod = new Proizvod();
        proizvod.setId(id);
        proizvod.setNaziv(naziv);
        proizvod.setOpis(opis);
        proizvod.setCena(cena);
        proizvod.setTip(tip);

        proizvodDAO.updateProizvod(proizvod);
        response.sendRedirect(request.getContextPath() + "/admin/proizvodi");
    }

    private void deleteProizvod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        proizvodDAO.deleteProizvod(id);
        response.sendRedirect(request.getContextPath() + "/admin/proizvodi");
    }
}
