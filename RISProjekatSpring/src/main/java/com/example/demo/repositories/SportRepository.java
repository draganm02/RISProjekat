package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Sport;

public interface SportRepository extends JpaRepository<Sport, Integer> {

}
