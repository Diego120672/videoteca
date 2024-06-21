package com.corso.videoteca.repositories;

import com.corso.videoteca.entities.Play;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRepository extends JpaRepository<Play, Long> {
}