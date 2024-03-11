package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKorisnik;

	private String email;

	private String ime;

	private String jmbg;

	private String korisnickoIme;

	private String lozinka;

	private BigDecimal novac;

	private String prezime;

	//bi-directional many-to-one association to Uloga
	@ManyToOne
	private Uloga uloga;

	//bi-directional many-to-one association to Tiket
	@OneToMany(mappedBy="korisnik")
	private List<Tiket> tikets;

	public Korisnik() {
	}

	public int getIdKorisnik() {
		return this.idKorisnik;
	}

	public void setIdKorisnik(int idKorisnik) {
		this.idKorisnik = idKorisnik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getJmbg() {
		return this.jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return this.lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public BigDecimal getNovac() {
		return this.novac;
	}

	public void setNovac(BigDecimal novac) {
		this.novac = novac;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Uloga getUloga() {
		return this.uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public List<Tiket> getTikets() {
		return this.tikets;
	}

	public void setTikets(List<Tiket> tikets) {
		this.tikets = tikets;
	}

	public Tiket addTiket(Tiket tiket) {
		getTikets().add(tiket);
		tiket.setKorisnik(this);

		return tiket;
	}

	public Tiket removeTiket(Tiket tiket) {
		getTikets().remove(tiket);
		tiket.setKorisnik(null);

		return tiket;
	}
	
	public void skiniSaRacuna(BigDecimal vrednost) {
		this.novac = this.novac.subtract(vrednost);
	}
	
	public void uplatiNaRacun(int iznos) {
		this.novac = this.novac.add(new BigDecimal(iznos));
	}

	public void uplatiNaRacun(BigDecimal dobitak) {
		this.novac = this.novac.add(dobitak);
		
	}

}