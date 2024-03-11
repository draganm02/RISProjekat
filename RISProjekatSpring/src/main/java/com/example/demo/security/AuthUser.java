package com.example.demo.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.Korisnik;
import model.Uloga;

public class AuthUser implements UserDetails {
	private final Korisnik user;
	private String ime;
	private String prezime;
	private String email;
	private String jmbg;
	private String korisnickoIme;
	private String lozinka;
	private BigDecimal novac;
	private Uloga uloga;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthUser(Korisnik k) {
		this.user = k;
		this.setIme(k.getIme());
		this.setPrezime(k.getPrezime());
		this.setNovac(k.getNovac());
		this.setEmail(k.getEmail());
		this.setJmbg(k.getJmbg());
		this.setLozinka(k.getLozinka());
		this.setKorisnickoIme(k.getKorisnickoIme());
		this.setUloga(k.getUloga());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUloga().getNazivUloga()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getLozinka();
	}

	@Override
	public String getUsername() {
		return user.getKorisnickoIme();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Korisnik getUser() {
		return user;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public BigDecimal getNovac() {
		return novac;
	}

	public void setNovac(BigDecimal bigDecimal) {
		this.novac = bigDecimal;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
