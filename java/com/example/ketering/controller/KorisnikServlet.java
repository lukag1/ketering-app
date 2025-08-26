package com.example.ketering.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.ketering.dao.KorisnikDAO;
import com.example.ketering.model.Korisnik;
import com.example.ketering.model.Uloga;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/korisnici")
public class KorisnikServlet extends HttpServlet {
    private KorisnikDAO korisnikDAO;

    public void init() {
        korisnikDAO = new KorisnikDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    insertKorisnik(request, response);
                    break;
                case "delete":
                    deleteKorisnik(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateKorisnik(request, response);
                    break;
                default:
                    listKorisnici(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listKorisnici(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Korisnik> listaKorisnika = korisnikDAO.getAllKorisnici();
        request.setAttribute("listaKorisnika", listaKorisnika);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/korisnici-list.jsp");
        dispatcher.forward(request, response);
    }

    // ISPRAVKA: Metoda za prikaz forme za NOVOG korisnika
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // Forma za novog korisnika takođe mora da dobije listu uloga
        List<Uloga> listaUloga = korisnikDAO.getAllUloge();
        request.setAttribute("listaUloga", listaUloga);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/korisnik-forma.jsp");
        dispatcher.forward(request, response);
    }

    // ISPRAVKA: Metoda za prikaz forme za IZMENU korisnika
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Korisnik postojeciKorisnik = korisnikDAO.getKorisnikById(id);
        List<Uloga> listaUloga = korisnikDAO.getAllUloge();
        
        // Koristimo "korisnikZaFormu" da ne bi došlo do konflikta sa sesijom
        request.setAttribute("korisnikZaFormu", postojeciKorisnik);
        request.setAttribute("listaUloga", listaUloga);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/korisnik-forma.jsp");
        dispatcher.forward(request, response);
    }

    private void insertKorisnik(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String email = request.getParameter("email");
        String lozinka = request.getParameter("lozinka");
        int ulogaId = Integer.parseInt(request.getParameter("ulogaId"));

        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setIme(ime);
        noviKorisnik.setPrezime(prezime);
        noviKorisnik.setEmail(email);
        noviKorisnik.setLozinka(lozinka); // U pravoj aplikaciji, ovde bi se lozinka heširala
        Uloga uloga = new Uloga();
        uloga.setId(ulogaId);
        noviKorisnik.setUloga(uloga);

        korisnikDAO.addKorisnik(noviKorisnik);
        response.sendRedirect(request.getContextPath() + "/admin/korisnici");
    }

    private void updateKorisnik(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String email = request.getParameter("email");
        String lozinka = request.getParameter("lozinka");
        int ulogaId = Integer.parseInt(request.getParameter("ulogaId"));

        Korisnik korisnik = korisnikDAO.getKorisnikById(id);
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setEmail(email);

        if (lozinka != null && !lozinka.isEmpty()) {
            korisnik.setLozinka(lozinka);
        }

        Uloga uloga = new Uloga();
        uloga.setId(ulogaId);
        korisnik.setUloga(uloga);

        korisnikDAO.updateKorisnik(korisnik);
        response.sendRedirect(request.getContextPath() + "/admin/korisnici");
    }

    private void deleteKorisnik(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        korisnikDAO.deleteKorisnik(id);
        response.sendRedirect(request.getContextPath() + "/admin/korisnici");
    }
}