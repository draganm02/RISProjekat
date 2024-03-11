package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.ServisRadnik;

import jakarta.servlet.http.HttpServletRequest;
import model.Tiket;

@Controller
@RequestMapping("/radnik/")
public class KontrolerRadnik {

	@Autowired
	ServisRadnik service;
	
	@GetMapping("radnik")
	public String radnik(HttpServletRequest request) {
		return "radnik";
	}
	
	@GetMapping("uplatiSredstvaForma")
	public String uplatiSredstvaForma(HttpServletRequest request) {
		return "uplatiSredstva";
	}
	
	@PostMapping("uplatiSredstva")
	public String uplatiSredstva(@RequestParam("korIme") String ussername, @RequestParam("iznos") int iznos, HttpServletRequest request) {
		String poruka = service.uplatiSredstva(ussername, iznos);
		request.setAttribute("poruka", poruka);
		return "uplatiSredstva";
	}
	
	@GetMapping("proveriTiketForma")
	public String proveriTiketForma(HttpServletRequest request) {
		return "proveriTiket";
	}
	
	@PostMapping("proveriTiket")
	public String proveriTiket(@RequestParam("tiketId") int tiketId, HttpServletRequest request) {
		Object[] niz = service.proveriTiket(tiketId);
		Tiket tiket = (Tiket)niz[0];
		String st = (String)niz[1];
		if(tiket == null)
			request.setAttribute("greska", "Pogrešan ID tiketa");
		else {
			request.setAttribute("tiket", tiket);
			request.setAttribute("st", st);
		}
		return "proveriTiket";
	}
}
