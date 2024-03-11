package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Kvota;
import model.Utakmica;

public interface UtakmicaRepository extends JpaRepository<Utakmica, Integer> {

	@Query("select u from Utakmica u where u.sport.idSport = :idS and u.vremeUtakmica > :datum")
	List<Utakmica> findBySport(@Param("idS")Integer id, @Param("datum")Date datum);
	
	@Query("select u from Utakmica u where u.vremeUtakmica < :datum and u.poeniDomaci is null and u.poeniGost is null order by u.vremeUtakmica")
	List<Utakmica> findAllFinished(@Param("datum")Date datum);

	@Query("select u from Utakmica u where u.vremeUtakmica > :datum order by u.vremeUtakmica")
	List<Utakmica> findAllFuture(@Param("datum")Date date);

}
