package com.corso.videoteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corso.videoteca.entities.Film;
import com.corso.videoteca.entities.Genre;
import com.corso.videoteca.repositories.FilmRepository;
import com.corso.videoteca.repositories.GenreRepository;

@RequestMapping("/genre")
@Controller
public class GenreController {

	@Autowired
	private GenreRepository gr;
	
	@Autowired
	private FilmRepository fr;

	@GetMapping("/")
	public String index(Model model) {
		//devo caricare la lista di tutti i film dal database
		// la metto nel model
		// restituisco una view di Thymeleaf
		
		 List<Film> ls = fr.findAll();
		 model.addAttribute("films",ls);
		 
		 List<Genre> lg = gr.findAll();
		 model.addAttribute("genres",lg);
		 
		 return "genre/index"; // resources/templates/film/index.html
	}
	
	//URL = RequestMapping + mapping del singolo metodo
	@GetMapping("/create")   // URL /film/create
	public String create(Model model) {
		System.out.println("GET GENRE CREATE");
		model.addAttribute("form", new Genre());
		return "genre/create";
	}
	
	@PostMapping("/create")   // URL  /film/create
	public String store(Model model,Genre form) {
		System.out.println("POST GENRE CREATE");
		System.out.println(form);
		
		Genre creato = gr.save(form);
		
		
		System.out.println(creato);
		
		return "redirect:/genre/update";  //redirect: vai a endpoint /film/
	}
	
	@GetMapping("/update")   // {id} è una path Variable
	public String edit(Model model) {
		
		//
		 List<Genre> lg = gr.findAllByOrderByName();
		 model.addAttribute("genres",lg);
		
		return "genre/update";
	}
	
	@GetMapping("/update/{id}")   // {id} è una path Variable
	public String edit(@PathVariable Long id, Model model) {
		
		//
	    Genre g = gr.findById(id).get();
		
	    model.addAttribute("form",g);
		
		return "genre/update";
	}

	@PostMapping("/update/{id}")
	public String update(Model model,Genre form,@PathVariable Long id ) {
		System.out.println("POST GENERE UPDATE");
		System.out.println(form);
		
		form.setId(id); //IMPOSTA L'ID 
		Genre update = gr.save(form);
		System.out.println(update); 
		
		return "redirect:/genre/update";  //redirect: vai a endpoint /film/
	}
	
	@GetMapping("/delete/{id}")   // {id} è una path Variable
	public String delete(@PathVariable Long id) {
		
		//
	    Genre g = gr.findById(id).get();
	    gr.delete(g);
		System.out.println("Cancellazione genere prima del redirect");
		return "redirect:/genre/update";
	}
}
