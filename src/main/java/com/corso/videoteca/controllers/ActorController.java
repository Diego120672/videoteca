package com.corso.videoteca.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corso.videoteca.entities.Actor;
import com.corso.videoteca.entities.Film;
import com.corso.videoteca.entities.Genre;
import com.corso.videoteca.repositories.ActorRepository;
import com.corso.videoteca.repositories.GenreRepository;

@RequestMapping("/actor")
@Controller
public class ActorController {

	@Autowired
	private ActorRepository ar;
	
	@GetMapping("/")
	public String index(Model model, Actor actor) {
		//devo caricare la lista di tutti i film dal database
		// la metto nel model
		// restituisco una view di Thymeleaf
		
		 Set<Actor> la = ar.findAllByOrderByFirstname();
		 model.addAttribute("actors",la);

		 
		 return "actor/index"; // resources/templates/film/index.html
	}
	
	//URL = RequestMapping + mapping del singolo metodo
	@GetMapping("/create")   // URL /film/create
	public String create(Model model) {
		System.out.println("GET ACTOR CREATE");
		model.addAttribute("form", new Actor());
		return "actor/create";
	}
	
	@PostMapping("/create")   // URL  /film/create
	public String store(Model model,Actor form) {
		System.out.println("POST ACTOR CREATE");
		System.out.println(form);
		
		Actor creato = ar.save(form);
		
		
		System.out.println(creato);
		
		return "redirect:/actor/";  //redirect: vai a endpoint /film/
	}
	
	@GetMapping("/update/{id}")   // {id} è una path Variable
	public String edit(@PathVariable Long id, Model model) {
		
		//
	    Actor a = ar.findById(id).get();
		
	    model.addAttribute("form",a);
		
		return "actor/update";
	}

	@PostMapping("/update/{id}")
	public String update(Model model,Actor form,@PathVariable Long id ) {
		System.out.println("POST ATTORE UPDATE");
		System.out.println(form);
		
		form.setId(id); //IMPOSTA L'ID 
		Actor update = ar.save(form);
		System.out.println(update); 
		
		return "redirect:/actor/";  //redirect: vai a endpoint /film/
	}

	@GetMapping("/delete/{id}")   // {id} è una path Variable
	public String delete(@PathVariable Long id) {
		
		//
	    Actor a = ar.findById(id).get();
	    ar.delete(a);
		System.out.println("Cancellazione genere prima del redirect");
		return "redirect:/actor/";
	}

	
}
