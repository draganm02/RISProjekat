package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
