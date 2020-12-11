package com.example.rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Turn;

public interface TurnRepo extends JpaRepository<Turn, Integer> {

}
