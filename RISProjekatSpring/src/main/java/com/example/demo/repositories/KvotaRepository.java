package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Kvota;

public interface KvotaRepository extends JpaRepository<Kvota, Integer> {

	@Query("select k from Kvota k where k.utakmica.idUtakmica = :idU")
	List<Kvota> findByUtakmica(@Param("idU")Integer id);

}
