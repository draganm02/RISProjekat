package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.UlogaRepository;

import model.Korisnik;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	UlogaRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik k = kr.findByKorisnickoIme(username);
		UserDetails ud = new AuthUser(k);
		return ud;
	}

}
