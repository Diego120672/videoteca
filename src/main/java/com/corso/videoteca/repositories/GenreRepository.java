package com.corso.videoteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corso.videoteca.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	
	
	public List<Genre> findAllByOrderByName();
}
