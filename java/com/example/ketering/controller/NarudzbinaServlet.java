package com.example.ketering.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ketering.dao.NarudzbinaDAO;
import com.example.ketering.model.Korisnik;
import com.example.ketering.model.Narudzbina;
import com.example.ketering.model.StavkaKorpe;
import com.example.ketering.model.StavkaNarudzbine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/narudzbina")
public class NarudzbinaServlet extends HttpServlet {
    private NarudzbinaDAO narudzbinaDAO;

    @Override
    public void init() {
        narudzbinaDAO = new NarudzbinaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        Map<Integer, StavkaKorpe> korpa = (Map<Integer, StavkaKorpe>) session.getAttribute("korpa");

        if (korisnik == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (korpa == null || korpa.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/korpa.jsp");
            return;
        }
        
        // Preuzimanje adrese iz forme
        String adresaDostave = request.getParameter("adresaDostave");
        if (adresaDostave == null || adresaDostave.trim().isEmpty()) {
            request.setAttribute("greska", "Adresa za dostavu je obavezna.");
            request.getRequestDispatcher("/korpa.jsp").forward(request, response);
            return;
        }

        // 1. Kreiranje narudžbine sa ispravnim tipovima podataka
        BigDecimal ukupnaCena = korpa.values().stream()
                                     .map(stavka -> BigDecimal.valueOf(stavka.getProizvod().getCena()).multiply(BigDecimal.valueOf(stavka.getKolicina())))
                                     .reduce(BigDecimal.ZERO, BigDecimal::add);

        Narudzbina narudzbina = new Narudzbina();
        narudzbina.setKorisnikId(korisnik.getId());
        narudzbina.setDatum(new Timestamp(System.currentTimeMillis())); // ISPRAVKA: Koristi se Timestamp
        narudzbina.setUkupnaCena(ukupnaCena); // ISPRAVKA: Koristi se BigDecimal
        narudzbina.setAdresaDostave(adresaDostave); // DODATO: Postavljanje adrese
        narudzbina.setStatus("U obradi");

        // 2. Priprema liste stavki za upis u bazu
        List<StavkaNarudzbine> stavkeNarudzbine = new ArrayList<>();
        for (StavkaKorpe stavkaKorpe : korpa.values()) {
            StavkaNarudzbine stavkaNarudzbine = new StavkaNarudzbine();
            stavkaNarudzbine.setProizvodId(stavkaKorpe.getProizvod().getId());
            stavkaNarudzbine.setKolicina(stavkaKorpe.getKolicina());
            // ISPRAVKA: Ispravan naziv metode i konverzija u BigDecimal
            stavkaNarudzbine.setCenaPoKomadu(BigDecimal.valueOf(stavkaKorpe.getProizvod().getCena()));
            stavkeNarudzbine.add(stavkaNarudzbine);
        }

        // 3. Čuvanje kompletne narudžbine kroz transakciju
        boolean uspeh = narudzbinaDAO.saveKompletnaNarudzbina(narudzbina, stavkeNarudzbine);

        if (uspeh) {
            session.removeAttribute("korpa");
            session.setAttribute("poruka", "Vaša narudžbina je uspešno primljena!");
            response.sendRedirect(request.getContextPath() + "/potvrda.jsp");
        } else {
            request.setAttribute("greska", "Došlo je do greške prilikom procesiranja vaše narudžbine. Molimo pokušajte ponovo.");
            request.getRequestDispatcher("/korpa.jsp").forward(request, response);
        }
    }
}
