package com.example.demo.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.KvotaRepository;
import com.example.demo.repositories.SportRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.repositories.TiketRepository;
import com.example.demo.repositories.UtakmicaRepository;

import model.Igra;
import model.Korisnik;
import model.Kvota;
import model.Sport;
import model.Tiket;
import model.Utakmica;

@Service
public class ServisKladjenje {

	@Autowired
	SportRepository sportRep;

	@Autowired
	UtakmicaRepository utakmicaRep;

	@Autowired
	KvotaRepository kvotaRep;

	@Autowired
	StatusRepository statusRep;

	@Autowired
	TiketRepository tiketRep;
	
	@Autowired
	KorisnikRepository korisnikRep;
	
	public List<Sport> getSportovi() {
		return sportRep.findAll();
	}

	public Sport getSport(Integer id) {
		return sportRep.findById(id).get();
	}

	public List<Utakmica> getUtakmice(Integer id) {
		return utakmicaRep.findBySport(id, new Date());
	}

	public Utakmica getUtakmica(Integer id) {
		return utakmicaRep.findById(id).get();
	}

	public List<Kvota> getKvote(Integer id) {
		return kvotaRep.findByUtakmica(id);
	}

	public List<Kvota> dodajKvotu(List<Kvota> lista, Integer id) {
		if (lista == null)
			lista = new ArrayList<Kvota>();

		// da li vec postoji u listi
		for (Kvota k : lista)
			if (k.getIdKvota() == id) {
				lista.remove(k);
				return lista;
			}

		// da li postoji vec ta utakmica
		Kvota kv = kvotaRep.findById(id).get();
		for (Kvota k : lista)
			if (k.getUtakmica().getIdUtakmica() == kv.getUtakmica().getIdUtakmica()) {
				lista.remove(k);
				lista.add(kv);
				return lista;
			}

		lista.add(kv);
		return lista;
	}

	public String uplatiTiket(List<Kvota> lista, Double ulog, String korIme) {
		if(lista == null || lista.isEmpty())
			return "Došlo je do greške";
		if(ulog == null || ulog < 20)
			return "Morate uneti ulog veći od 20 RSD";
		Korisnik kor = korisnikRep.findByKorisnickoIme(korIme);
		if(kor.getUloga().getIdUloga() == 2 && ulog > kor.getNovac().doubleValue())
			return "Nemate dovoljno novca na računu";
		Tiket t = new Tiket();
		t.setKvotas(new ArrayList<Kvota>());
		Date datum = new Date();
		double kvota = 1;
		for(Kvota k : lista) {
			if(datum.after(k.getUtakmica().getVremeUtakmica()))
				return "Utakmica je počela";
			kvota *= k.getKvotaKvota().doubleValue();
			t.getKvotas().add(k);
		}
		BigDecimal ulogBD = new BigDecimal(Double.valueOf(new DecimalFormat("#.##").format(ulog)));
		BigDecimal kvotaBD = new BigDecimal(Double.valueOf(new DecimalFormat("#.##").format(kvota)));
		BigDecimal dobitakBD = new BigDecimal(ulogBD.doubleValue() * kvotaBD.doubleValue());
		t.setDobitak(dobitakBD);
		t.setUplata(ulogBD);
		t.setKvotaTiket(kvotaBD);
		t.setVremeTiket(datum);
		t.setStatus(statusRep.findById(1).get());
		t.setKorisnik(kor);
		if(kor.getUloga().getIdUloga() == 2)
			kor.skiniSaRacuna(ulogBD);
		try {
			t = tiketRep.save(t);
			if(kor.getUloga().getIdUloga() == 2)
				korisnikRep.save(kor);
		} catch(Exception e) 
		{
			return "Došlo je do greške";
		}
		return "Tiket uspešno uplaćen. ID tiketa: " + t.getIdTiket();
	}
}
