package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Tiket;

public interface TiketRepository extends JpaRepository<Tiket, Integer> {

}
