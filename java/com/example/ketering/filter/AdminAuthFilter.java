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


@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        
        if (session == null || session.getAttribute("korisnik") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        String uloga = korisnik.getUloga().getNaziv();
        String path = httpRequest.getRequestURI();

        
        if ("admin".equalsIgnoreCase(uloga)) {
            chain.doFilter(request, response);
            return;
        }
        
        if ("menadzer".equalsIgnoreCase(uloga)) {
            if (path.endsWith("/korisnici")) {
                
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/admin.jsp?error=unauthorized");
            } else {
                
                chain.doFilter(request, response);
            }
            return;
        }

        
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/meni");
    }
}