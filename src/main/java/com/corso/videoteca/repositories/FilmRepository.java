package com.corso.videoteca.repositories;

import java.util.List;
import java.util.Set;

import com.corso.videoteca.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.corso.videoteca.entities.Film;

import jakarta.annotation.Nullable;


/**
 * https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
 */

public interface FilmRepository extends JpaRepository<Film, Long> {
	
	List<Film> findAllByOrderByTitle();

	List<Film> findByGenre_IdOrderByTitleAsc(Long id);
	
	List<Film> findByTitleLikeIgnoreCaseOrderByTitleAsc(String title);

	List<Film> findByGenre_IdAndTitleLikeIgnoreCaseOrderByTitleAsc(@Nullable Long id, @Nullable String title);

	//Dall'id Film troviamo il cast
	//Set<Actor> findByPlays_Film_IdOrderByLastnameAscFirstnameAsc(Long id);

}
