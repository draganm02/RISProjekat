package com.example.demo.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.IgraRepository;
import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.KvotaRepository;
import com.example.demo.repositories.SportRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.repositories.TiketRepository;
import com.example.demo.repositories.UlogaRepository;
import com.example.demo.repositories.UtakmicaRepository;

import model.Igra;
import model.Korisnik;
import model.Kvota;
import model.Sport;
import model.Status;
import model.Tiket;
import model.Utakmica;

@Service
public class ServisAdministracija {

	@Autowired
	UtakmicaRepository ur;
	
	@Autowired
	UlogaRepository ulr;

	@Autowired
	StatusRepository str;

	@Autowired
	KvotaRepository kr;
	
	@Autowired
	KorisnikRepository kor;

	@Autowired
	SportRepository sr;

	@Autowired
	IgraRepository ir;
	
	@Autowired
	TiketRepository tr;
	
	@Autowired
	PasswordEncoder pe;

	public List<Sport> getSportovi() {
		return sr.findAll();
	}
	
	public List<Utakmica> getZavrseneUtakmice() {
		return ur.findAllFinished(new Date());
	}

	public String dodajRezultat(int utakmica, int domacin, int gost) {
		Utakmica ut = ur.findById(utakmica).get();
		if (ut == null)
			return "Došlo je do greške";
		ut.setPoeniDomaci(domacin);
		ut.setPoeniGost(gost);

		Status dobitan = str.findById(2).get();
		Status gubitan = str.findById(3).get();
		List<Kvota> lista = ut.getKvotas();
		for (Kvota kv : lista) {
			if (kv.getIgra().getNazivIgra().equals("1")) {
				if (domacin > gost)
					kv.setStatus(dobitan);
				else
					kv.setStatus(gubitan);
			} else if (kv.getIgra().getNazivIgra().equals("X")) {
				if (domacin == gost)
					kv.setStatus(dobitan);
				else
					kv.setStatus(gubitan);
			} else if (kv.getIgra().getNazivIgra().equals("2")) {
				if (domacin < gost)
					kv.setStatus(dobitan);
				else
					kv.setStatus(gubitan);
			}
			try {
				kv = kr.save(kv);
			} catch (Exception e) {
				return "Došlo je do greške";
			}
			for(Tiket tik : kv.getTikets()) {
				if(kv.getStatus().getIdStatus() == 3) {
					tik.setStatus(gubitan);
					try {
						tr.save(tik);
					} catch (Exception e) {
						return "Došlo je do greške";
					}
				}
				else if(kv.getStatus().getIdStatus() == 2 && tik.getKorisnik().getUloga().getIdUloga() == 2) {
					boolean ok = true;
					for(Kvota kv2 : tik.getKvotas())
						if(kv2.getStatus().getIdStatus() != 2) {
							ok = false;
							break;
						}
					if(ok) {
						tik.setStatus(dobitan);
						try {
							tr.save(tik);
						} catch (Exception e) {
							return "Došlo je do greške";
						}
						tik.getKorisnik().uplatiNaRacun(tik.getDobitak());
						try {
							kor.save(tik.getKorisnik());
						} catch (Exception e) {
							return "Došlo je do greške";
						}
					}
				}
			}
		}

		try {
			ur.save(ut);
		} catch (Exception e) {
			return "Došlo je do greške";
		}
		
		return "Uspešno dodat rezultat";
	}

	public String dodajUtakmicu(String domacin, String gost, int sport, String vreme) {
		if (domacin == null || domacin.equals("") || gost == null || gost.equals(""))
			return "Pogrešno uneti domaćin i/ili gost";
		Utakmica utakmica = new Utakmica();
		utakmica.setTimDomaci(domacin);
		utakmica.setTimGost(gost);
		utakmica.setSport(sr.findById(sport).get());
		Date datum = null;
		try {
			datum = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(vreme);
		} catch (Exception e) {
			return "Greška";
		}
		utakmica.setVremeUtakmica(datum);
		try {
			ur.save(utakmica);
		} catch (Exception e) {
			return "Greška";
		}
		return "Uspešno dodata utakmica";
	}

	public List<Utakmica> getPredstojeceUtakmice() {
		return ur.findAllFuture(new Date());
	}

	public Utakmica getUtakmica(int id) {
		return ur.findById(id).get();
	}

	public List<Igra> getIgre() {
		return ir.findAll();
	}

	public String urediKvote(Map<String, String> parametri, Utakmica utakmica) {
		for (Map.Entry<String, String> entry : parametri.entrySet()) {
			int id = Integer.parseInt(entry.getKey().substring(3, entry.getKey().length()));
			BigDecimal bd;
			if (entry.getValue().equals(""))
				bd = new BigDecimal(0.00);
			else
				try {
					bd = new BigDecimal(entry.getValue());
				} catch (Exception e) {
					return "Uneli ste pogrešan format kvote";
				}
			Kvota kv = null;
			for (Kvota k : utakmica.getKvotas())
				if (k.getIgra().getIdIgra() == id) {
					kv = k;
					break;
				}
			
			if (kv != null && !kv.getKvotaKvota().equals(bd)) {
				if(bd.doubleValue() == 0) {
					try {
						kr.delete(kv);
					} catch(Exception e) {
						return "Greška";
					}
				}
				else {
					kv.setKvotaKvota(bd); 
					try {
						kr.save(kv);
					} catch(Exception e) {
						return "Greška";
					}
				}
			}
			else if(kv == null && bd.doubleValue() != 0) {
				Kvota k = new Kvota();
				k.setIgra(ir.findById(id).get());
				k.setKvotaKvota(bd);
				k.setTikets(new ArrayList<Tiket>());
				k.setUtakmica(utakmica);
				k.setStatus(str.findById(1).get());
				try {
					kr.save(k);
				} catch(Exception e) {
					return "Greška";
				}
			}
		}
		
		return "Uspešno promenjene kvote";
	}

	public String dodajKorisnika(Map<String, String> parametri) {
		if(parametri.get("ime").isBlank() || parametri.get("prezime").isBlank() || parametri.get("jmbg").isBlank() || 
				parametri.get("mail").isBlank() || parametri.get("korIme").isBlank())
			return "Niste popunili sva polja";
		String jmbg = parametri.get("jmbg").trim();
		if(jmbg.length() != 13)
			return "Neispravan jmbg";
		try {
			long jmbgL = Long.parseLong(jmbg);
		} catch (Exception e) {
			return "Neispravan jmbg";
		}
		Korisnik provera = null;
		try {
			provera = kor.findByKorisnickoIme(parametri.get("korIme"));
		} catch(Exception e) {
			provera = null;
		}
		if(provera != null) 
			return "Korisnik već postoji";
		String dan, mesec, godina;
		dan = jmbg.substring(0, 2);
		mesec = jmbg.substring(2, 4);
		godina = jmbg.substring(4, 7);
		if(godina.charAt(0) == '9')
			godina = "1" + godina;
		else if(godina.charAt(0) == '0')
			godina = "2" + godina;
		else
			return "Neispravan jmbg";
		Date sada = new Date();
		sada.setYear(sada.getYear() - 18);
		Date datumRodj;
		try {
			datumRodj = new SimpleDateFormat("yyyy-MM-dd").parse(godina + "-" + mesec + "-" + dan);
		} catch (Exception e) {
			return "Neispravan jmbg";
		}
		if(datumRodj.after(sada))
			return "Korisnik je maloletan";
		if(parametri.get("lozinka").length() < 8)
			return "Lozinka mora biti duža od 8 karaktera";
		if(!parametri.get("lozinka").equals(parametri.get("lozinka2")))
			return "Lozinke se ne podudaraju";
		String ussername = parametri.get("korIme").trim();
		if(ussername.contains(" "))
			return "Korisničko ime ne sme da sadrži razmak";
		if(parametri.get("lozinka").trim().contains(" "))
			return "Lozinka ne sme da sadrži razmak";
		for(Korisnik k : kor.findAll())
			if(k.getKorisnickoIme().equals(ussername))
				return "Korisničko ime je zauzeto";
		Korisnik novi = new Korisnik();
		novi.setIme(parametri.get("ime"));
		novi.setPrezime(parametri.get("prezime"));
		novi.setEmail(parametri.get("mail"));
		novi.setJmbg(jmbg);
		novi.setKorisnickoIme(ussername);
		novi.setLozinka(pe.encode( parametri.get("lozinka")));
		novi.setNovac(new BigDecimal(0));
		novi.setTikets(new ArrayList<Tiket>());
		int uloga = Integer.parseInt(parametri.get("tip"));
		novi.setUloga(ulr.findById(uloga).get());
		try {
			kor.save(novi);
		} catch(Exception e) {
			return "Greška";
		}
		return "Uspešno dodat novi korisnik";
	}

	public Korisnik nadjiKorisnika(String name) {
		return kor.findByKorisnickoIme(name);
	}

}
