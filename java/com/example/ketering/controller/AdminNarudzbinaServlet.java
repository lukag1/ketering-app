package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.ketering.dao.NarudzbinaDAO;
import com.example.ketering.model.Narudzbina;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/narudzbine")
public class AdminNarudzbinaServlet extends HttpServlet {
    private NarudzbinaDAO narudzbinaDAO;

    @Override
    public void init() {
        narudzbinaDAO = new NarudzbinaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "delete":
                    deleteNarudzbina(request, response);
                    break;
                case "updateStatus":
                    updateStatus(request, response);
                    break;
                default:
                    listNarudzbine(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in AdminNarudzbinaServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listNarudzbine(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Narudzbina> listaNarudzbina = narudzbinaDAO.getAllNarudzbine();
        request.setAttribute("listaNarudzbina", listaNarudzbina);
        request.getRequestDispatcher("/admin/narudzbine-lista.jsp").forward(request, response);
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        narudzbinaDAO.updateStatusNarudzbine(id, status);
        response.sendRedirect(request.getContextPath() + "/admin/narudzbine");
    }

    private void deleteNarudzbina(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        narudzbinaDAO.deleteNarudzbina(id);
        response.sendRedirect(request.getContextPath() + "/admin/narudzbine");
    }
}