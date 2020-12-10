package com.example.rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.games;

@Repository
public interface GameRepo extends JpaRepository<games, Integer> {

}
