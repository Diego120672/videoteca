package com.corso.videoteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "firstname", length = 100)
    private String firstname;

    @Size(max = 100)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Column(name = "dob")
    private LocalDate dob;

    @OneToMany(mappedBy = "actor")
    private Set<Play> plays = new LinkedHashSet<>();
    
    public String getIsoDob() {

        if(this.dob == null)
            return "";
        return this.dob.format(DateTimeFormatter.ISO_DATE);
    }

}