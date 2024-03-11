package com.example.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;

import model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	
	/*@Query("select k from Korisnik k where k.korisnickoIme = :ussername")
	Korisnik findByKorisnickoIme(@Param("ussername")String ussername);*/

	//Korisnik findByKorisnickoIme(String korisnickoIme);
	Korisnik findByKorisnickoIme(String korisnickoIme);
}
