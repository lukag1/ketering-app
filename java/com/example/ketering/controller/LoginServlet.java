package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.example.ketering.dao.KorisnikDAO;
import com.example.ketering.model.Korisnik;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private KorisnikDAO korisnikDAO;

    @Override
    public void init() {
        korisnikDAO = new KorisnikDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Prosleđuje na JSP stranicu za prikaz forme
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String lozinka = request.getParameter("lozinka");

        try {
            Korisnik korisnik = korisnikDAO.getKorisnikByEmailAndLozinka(email, lozinka);

            if (korisnik != null) {
                // Korisnik pronađen, kreiraj sesiju
                HttpSession session = request.getSession();
                session.setAttribute("korisnik", korisnik);

                // Preusmeravanje na osnovu uloge
                String ulogaNaziv = korisnik.getUloga().getNaziv();

                if ("admin".equalsIgnoreCase(ulogaNaziv)) {
                    response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");
                } else if ("menadzer".equalsIgnoreCase(ulogaNaziv) || "menadžer".equalsIgnoreCase(ulogaNaziv)) {
                    // Menadžera preusmeravamo direktno na upravljanje proizvodima
                    response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");
                } else { // Klijent i ostali
                    response.sendRedirect(request.getContextPath() + "/meni");
                }

            } else {
                // Korisnik nije pronađen ili je lozinka netačna
                request.setAttribute("errorMessage", "Pogrešan email ili lozinka.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Došlo je do greške sa bazom podataka.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
