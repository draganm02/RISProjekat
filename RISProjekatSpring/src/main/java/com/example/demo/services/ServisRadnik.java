package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.repositories.TiketRepository;

import model.Korisnik;
import model.Kvota;
import model.Tiket;

@Service
public class ServisRadnik {
	
	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	TiketRepository tr;
	
	@Autowired
	StatusRepository sr;
	
	public String uplatiSredstva(String ussername, int iznos) {
		if(iznos < 0)
			return "Pogrešan iznos";
		Korisnik k = null;
		try {
			k = kr.findByKorisnickoIme(ussername);
		} catch(Exception e) {
			k = null;
		}
		if(k == null)
			return "Pogrešno korisničko ime";
		if(k.getUloga().getIdUloga() != 2)
			return "Ne mogu se uplatiti sredstva nalogu koji nije korisnički";
		k.uplatiNaRacun(iznos);
		try {
			kr.save(k);
		} catch (Exception e) {
			return "Greška";
		}
		return "Uspešno uplaćena sredstva";
	}

	public Object[] proveriTiket(int tiketId) {
		Tiket tiket = null;
		try {
			tiket = tr.findById(tiketId).get(); 
		} catch(Exception e) {
			tiket = null;
		}
		Object[] rez = new Object[2];
		rez[0] = tiket;
		rez[1] = "";
		if(tiket == null)
			return rez;
		if(tiket.getStatus().getIdStatus() == 2) {
			rez[1] = "Isplaćen";
			return rez;
		}
		if(tiket.getStatus().getIdStatus() == 3) {
			rez[1] = "Gubitan";
			return rez;
		}
		boolean ok = true;
		boolean uToku = false;
		for(Kvota k : tiket.getKvotas()) {
			if(k.getStatus().getIdStatus() == 3) {
				ok = false;
				break;
			}
			if(k.getStatus().getIdStatus() == 1) {
				uToku = true;
			}
		}
		if(ok && uToku) {
			rez[1] = "U toku";
			return rez;
		}
		if(!ok) {
			rez[1] = "Gubitan";
			tiket.setStatus(sr.findById(3).get());
		} else {
			rez[1] = "Dobitan";
			tiket.setStatus(sr.findById(2).get());
		}
		try {
			tiket = tr.save(tiket);
			rez[0] = tiket;
		} catch(Exception e) {
			rez[1] = "Greška";
		}
		return rez;
	}
	
}
