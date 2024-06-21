package com.corso.videoteca.repositories;

import com.corso.videoteca.entities.Actor;
import com.corso.videoteca.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {


    Set<Actor> findByFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCaseOrderByLastnameAscFirstnameAsc(String firstname, String lastname);


    //Dal Film troviamo il cast
    Set<Actor> findByPlays_FilmOrderByLastnameAscFirstnameAsc(Film film);

    
    Set<Actor> findAllByOrderByFirstname();


}