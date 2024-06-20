package com.corso.videoteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corso.videoteca.dto.searchFilmDto;
import com.corso.videoteca.entities.Film;
import com.corso.videoteca.repositories.FilmRepository;
import com.corso.videoteca.repositories.GenreRepository;


/* 
 * Met    URL - END-POINT
 * GET   /film/ -> index e stampa tutti i film
 * GET   /film/create -> mostra il form di creazione film
 * POST	 /film/create -> riceve i dati del form film e crea un nuovo film	
 * GET   /film/update/{id}   -> Visualizza il form di aggiornamento del film con id={id}
 * POST  /film/update/{id}   -> RICEVE I DATI DAL FORM DI AGGIORNAMENTO E AGGIORNA IL DB
* GET/POST   /film/delete/{id}   -> Elimina il film con l'id = {id}
 */

@RequestMapping("/film")
@Controller
public class FilmController {
	
	
	@Autowired
	private FilmRepository fr;
	
	@Autowired
	private GenreRepository gr;
	
	@GetMapping("/")
	public String index(Model model) {
		//devo caricare la lista di tutti i film dal database
		// la metto nel model
		// restituisco una view di Thymeleaf
		
		 List<Film> ls = fr.findAllByOrderByTitle();
		 model.addAttribute("films",ls);
		 
		 model.addAttribute("genres", gr.findAllByOrderByName());
		 
		 return "film/index"; // resources/templates/film/index.html
	}

	//URL = RequestMapping + mapping del singolo metodo
	@GetMapping("/create")   // URL /film/create
	public String create(Model model) {
		System.out.println("GET FILM CREATE");
		model.addAttribute("form", new Film());
		
		model.addAttribute("genres", gr.findAllByOrderByName());
		
		return "film/create";
	}
	
	@PostMapping("/create")   // URL  /film/create
	public String store(Model model,Film form) {
		System.out.println("POST FILM CREATE");
		System.out.println(form);
		
		Film creato = fr.save(form);
		
		
		System.out.println(creato);
		
		return "redirect:/film/";  //redirect: vai a endpoint /film/
	}
	
	@GetMapping("/update/{id}")   // {id} è una path Variable
	public String edit(@PathVariable Long id, Model model) {
		
		//
	    Film f = fr.findById(id).get();
		
	    model.addAttribute("form",f);
	    
	    model.addAttribute("genres", gr.findAllByOrderByName());
		
		return "film/update";
	}
	
	
	@PostMapping("/update/{id}")
	public String update(Model model,Film form,@PathVariable Long id ) {
		System.out.println("POST FILM UPDATE");
		System.out.println(form);
		
		form.setId(id); //IMPOSTA L'ID 
		Film update = fr.save(form);
		System.out.println(update); 
		
		return "redirect:/film/";  //redirect: vai a endpoint /film/
	}
	
	@GetMapping("/delete/{id}")   // {id} è una path Variable
	public String delete(@PathVariable Long id) {
		
		//
	    Film f = fr.findById(id).get();
	    fr.delete(f);
		System.out.println("Cancellazione prima del redirect");
		return "redirect:/film/";
	}
	
	@GetMapping("search")
	public String search(Model model) {
		
		model.addAttribute("form",new searchFilmDto());
		model.addAttribute("genres", gr.findAllByOrderByName());

		
		return "film/search";
	}
	
	@PostMapping("search")
	public String search2(searchFilmDto form, Model model) {
		
		if (form.getTitle() != null && form.getTitle().length()>2) {
			form.setTitle("%"+form.getTitle()+"%");
		}
		if (form.getGenre_id() != null ) {
			List<Film> risultato = fr.findByGenre_IdAndTitleLikeIgnoreCaseOrderByTitleAsc(form.getGenre_id(),form.getTitle());
			model.addAttribute("films", risultato);
		}
		
		else {
			List <Film> risultato = fr.findByTitleLikeIgnoreCaseOrderByTitleAsc(form.getTitle());
			model.addAttribute("films", risultato);
		}
		
		
		return "film/index";
	}
}
