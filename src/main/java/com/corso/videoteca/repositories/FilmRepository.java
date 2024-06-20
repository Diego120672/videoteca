package com.corso.videoteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corso.videoteca.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
	
	List<Film>findAllByOrderByTitle();

}
