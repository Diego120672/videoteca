package com.corso.videoteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corso.videoteca.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	
	

}
