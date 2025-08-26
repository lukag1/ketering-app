package com.example.ketering.filter;

import java.io.IOException;

import com.example.ketering.model.Korisnik;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Ovaj filter štiti sve stranice na putanji /admin/*
 * Dozvoljava pristup samo korisnicima sa ulogom 'admin'.
 */
@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Provera da li je korisnik uopšte ulogovan
        if (session == null || session.getAttribute("korisnik") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Korisnik je ulogovan, proveravamo ulogu
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        String uloga = korisnik.getUloga().getNaziv();
        String path = httpRequest.getRequestURI();

        // Admin ima pristup svemu
        if ("admin".equalsIgnoreCase(uloga)) {
            chain.doFilter(request, response);
            return;
        }
        // Menadžer ima ograničen pristup
        if ("menadzer".equalsIgnoreCase(uloga)) {
            if (path.endsWith("/korisnici")) {
                // Menadžer ne može da pristupi upravljanju korisnicima
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/admin.jsp?error=unauthorized");
            } else {
                // Za sve ostale /admin/* stranice, menadžer ima pristup
                chain.doFilter(request, response);
            }
            return;
        }

        // Ako je ulogovan neko treći (npr. klijent), preusmeri ga
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/meni");
    }
}