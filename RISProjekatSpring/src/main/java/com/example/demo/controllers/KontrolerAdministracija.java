package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.ServisAdministracija;

import jakarta.servlet.http.HttpServletRequest;
import model.Utakmica;

@Controller
@RequestMapping("/administracija/")
public class KontrolerAdministracija {
	
	@Autowired
	ServisAdministracija service;
	
	@GetMapping("admin")
	public String admin(HttpServletRequest request) {
		return "administrator";
	}
	
	@GetMapping("getZavrseneUtakmice")
	public String getZavrseneUtakmice(HttpServletRequest request) {
		request.getSession().removeAttribute("utakmica");
		request.getSession().setAttribute("utakmice", service.getZavrseneUtakmice());
		return "dodajRezultat";
	}
	
	@PostMapping("dodajRezultatForma")
	public String dodajRezultatForma(@RequestParam("utakmica") int id, HttpServletRequest request) {
		request.getSession().setAttribute("utakmica", id);
		return "dodajRezultat";
	}

	@PostMapping("dodajRezultat")
	public String dodajRezultat(@RequestParam("domacin") int domacin, @RequestParam("gost") int gost, HttpServletRequest request) {
		int utakmica = (int)request.getSession().getAttribute("utakmica");
		String poruka = service.dodajRezultat(utakmica, domacin, gost);
		request.setAttribute("poruka", poruka);
		return "dodajRezultat";
	}
	
	@GetMapping("dodajUtakmicuForma")
	public String dodajUtakmicuForma(HttpServletRequest request) {
		request.getSession().setAttribute("sportovi", service.getSportovi());
		return "dodajUtakmicu";
	}
	
	@PostMapping("dodajUtakmicu")
	public String dodajUtakmicu(@RequestParam("domaci") String domacin, @RequestParam("gost") String gost, 
			@RequestParam("sport") int sport, @RequestParam("vreme") String vreme, HttpServletRequest request) {
		String poruka = service.dodajUtakmicu(domacin, gost, sport, vreme);
		request.setAttribute("poruka", poruka);
		return "dodajUtakmicu";
	}
	
	@GetMapping("getPredstojeceUtakmice")
	public String getPredstojeceUtakmice(HttpServletRequest request) {
		request.getSession().removeAttribute("utakmica");
		request.getSession().setAttribute("utakmice", service.getPredstojeceUtakmice());
		return "urediKvote";
	}
	
	@PostMapping("urediKvoteForma")
	public String urediKvoteForma(@RequestParam("utakmica") int id, HttpServletRequest request) {
		request.getSession().removeAttribute("poruka");
		request.getSession().setAttribute("utakmica", service.getUtakmica(id));
		request.getSession().setAttribute("igre", service.getIgre());
		return "urediKvote";
	}
	
	@PostMapping("urediKvote")
	public String urediKvote(@RequestParam Map<String, String> parametri, HttpServletRequest request) {
		String poruka = service.urediKvote(parametri, (Utakmica)request.getSession().getAttribute("utakmica"));
		request.getSession().removeAttribute("utakmica");
		request.setAttribute("poruka", poruka);
		return "urediKvote";
	}
	
	@GetMapping("dodajKorisnikaForma")
	public String dodajKorisnikaForma(HttpServletRequest request) {
		return "dodajKorisnika";
	}
	
	@PostMapping("dodajKorisnika")
	public String dodajKorisnikaForma(@RequestParam Map<String, String> parametri, HttpServletRequest request) {
		String poruka = service.dodajKorisnika(parametri);
		request.setAttribute("poruka", poruka);
		return "dodajKorisnika";
	}
	
	@GetMapping("mojProfil")
	public String mojProfil(HttpServletRequest request) {
		request.setAttribute("korisnik", service.nadjiKorisnika(request.getUserPrincipal().getName()));
		return "mojProfil";
	}
	
}
