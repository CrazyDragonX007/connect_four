package com.example.rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.turn;

public interface TurnRepo extends JpaRepository<turn, Integer> {

}
