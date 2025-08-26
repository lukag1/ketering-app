package com.example.ketering.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Dohvati sesiju, ali ne kreiraj novu ako ne postoji
        if (session != null) {
            session.invalidate(); // Poništi sesiju
        }
        response.sendRedirect("login.jsp"); // Preusmeri na stranicu za prijavu
    }
}
