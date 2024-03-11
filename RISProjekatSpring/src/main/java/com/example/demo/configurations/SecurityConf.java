package com.example.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConf {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> 
				req.requestMatchers(new AntPathRequestMatcher("/kladjenje/**")).hasAnyRole("Korisnik", "Radnik")
				.requestMatchers(new AntPathRequestMatcher("/administracija/dodajKorisnikaForma")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/administracija/dodajKorisnika")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/administracija/mojProfil")).hasAnyRole("Korisnik", "Radnik", "Administrator")
				.requestMatchers(new AntPathRequestMatcher("/administracija/**")).hasRole("Administrator")
				.requestMatchers(new AntPathRequestMatcher("/radnik/**")).hasRole("Radnik")
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll().anyRequest().authenticated()
				)

			.formLogin(form -> form.loginPage("/login.jsp").permitAll().loginProcessingUrl("/login")
						.defaultSuccessUrl("/index.jsp"))
			.logout(l -> l.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
			.csrf(csfr -> csfr.disable());
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

/*
 * new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN") .requestMatchers(new
 * AntPathRequestMatcher("/user/**")).hasAnyRole("ADMIN", "USER", "AVIOCOMPANY")
 * .requestMatchers(new
 * AntPathRequestMatcher("/booking/**")).hasAnyRole("ADMIN", "USER",
 * "AVIOCOMPANY") .requestMatchers(new
 * AntPathRequestMatcher("/avio/**")).hasAnyRole("ADMIN", "AVIOCOMPANY")
 * .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()
 * .requestMatchers(new
 * AntPathRequestMatcher("/**")).permitAll().anyRequest().authenticated()
 */
