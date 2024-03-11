package com.example.demo.controllers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.ServisKladjenje;

import jakarta.servlet.http.HttpServletRequest;
import model.Igra;
import model.Kvota;
import model.Tiket;
import model.Utakmica;

@Controller
@RequestMapping("/kladjenje/")
public class KontrolerKladjenje {

	@Autowired
	ServisKladjenje service;

	@GetMapping("getSportovi")
	public String getSportovi(HttpServletRequest request) {
		request.getSession().setAttribute("sportovi", service.getSportovi());
		request.getSession().removeAttribute("kvote");
		request.getSession().removeAttribute("odabranaUtakmica");
		return "prikazSportova";
	}

	@GetMapping("getUtakmice")
	public String getUtakmice(@RequestParam("idSporta") Integer id, HttpServletRequest request) {
		request.getSession().setAttribute("utakmice", service.getUtakmice(id));
		request.getSession().setAttribute("odabraniSport", service.getSport(id));
		return "prikazSportova";
	}

	@GetMapping("getKvote")
	public String getKvote(@RequestParam("idUtakmice") Integer id, HttpServletRequest request) {
		Utakmica u = (Utakmica)(request.getSession().getAttribute("odabranaUtakmica"));
		if (u == null || u.getIdUtakmica() != id) {
			request.getSession().setAttribute("kvote", service.getKvote(id));
			request.getSession().setAttribute("odabranaUtakmica", service.getUtakmica(id));
		}
		else {
			request.getSession().removeAttribute("kvote");
			request.getSession().removeAttribute("odabranaUtakmica");
		}
		return "prikazSportova";
	}
	
	@PostMapping("dodajKvotu")
	public String dodajKvotu(@RequestParam("idKvote") Integer id, HttpServletRequest request) {
		List<Kvota> lista = (List<Kvota>)request.getSession().getAttribute("tiket");
		lista = service.dodajKvotu(lista, id);
		double kvota = 1;
		for(Kvota k : lista) {
			kvota *= k.getKvotaKvota().doubleValue();
		}
		request.getSession().setAttribute("tiket", lista);
		if(kvota > 1)
			request.getSession().setAttribute("kvota", Double.valueOf(new DecimalFormat("#.##").format(kvota)));
		else
			request.getSession().removeAttribute("kvota");
		return "prikazSportova";
	}
	
	@PostMapping("prikazTiketa")
	public String prikazTiketa(HttpServletRequest request) {
		return "prikazTiketa";
	}
	
	@PostMapping("uplatiTiket")
	public String uplatiTiket(@RequestParam("ulog") Double ulog, HttpServletRequest request) {
		String poruka = service.uplatiTiket((List<Kvota>)request.getSession().getAttribute("tiket"), ulog, request.getUserPrincipal().getName());
		request.getSession().removeAttribute("kvota");
		request.getSession().removeAttribute("odabranaUtakmica");
		request.getSession().removeAttribute("tiket");
		request.setAttribute("poruka", poruka);
		return "prikazSportova";
	}
}
