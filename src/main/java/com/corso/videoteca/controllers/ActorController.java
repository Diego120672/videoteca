package com.corso.videoteca.controllers;

import java.util.Set;

import com.corso.videoteca.entities.Play;
import com.corso.videoteca.repositories.FilmRepository;
import com.corso.videoteca.repositories.PlayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corso.videoteca.entities.Actor;
import com.corso.videoteca.repositories.ActorRepository;

@RequestMapping("/actor")
@Controller
public class ActorController {

	@Autowired
	private ActorRepository ar;
	@Autowired
	private FilmRepository fr;
	@Autowired
	private PlayRepository pr;
	
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

	@GetMapping("plays/{id}")   // {id} è una path Variable
	public String plays(@PathVariable Long id, Model model) {

		Actor actor = ar.findById(id).get();

		model.addAttribute(actor);
		Play form = new Play();

		form.setId(null);
		model.addAttribute("form", form);
		model.addAttribute("films", fr.findAllByOrderByTitle());


		return "actor/plays";
	}


	@PostMapping("plays/{id_actor}")
	public String storePlays(Model model,Play form,@PathVariable Long id_actor ) {
		System.out.println("POST Play");
		System.out.println("Plays -> "+"form.getActor().getId()"+" "+form.getFilm().getId()+" "+form.getRole()+" "+form.getId());
		Play p=new Play();

		Actor actor = ar.findById(id_actor).get();
		form.setActor(actor);
		System.out.println("Plays -> "+form.getActor().getId()+" "+form.getFilm().getId()+" "+form.getRole()+" "+form.getId());
		p=pr.save(form);

		System.out.println("id play "+p.getId()+" "+form.getId());

		return "redirect:/actor/plays/"+ form.getActor().getId();
	}



	@GetMapping("plays_update/{id_play}")   // {id} è una path Variable
	public String playsUpdate(@PathVariable Long id_play, Model model) {

		Play play = pr.findById(id_play).get();

		model.addAttribute("form",play);
		model.addAttribute("films", fr.findAllByOrderByTitle());

		System.out.println("------------------A "+play.getActor().getId()+"F "+play.getFilm().getId()+"R "+play.getRole());

		return "actor/plays_update";
	}

	@PostMapping("plays_update/{id}/{id_actor}")
	public String storePlaysUpdate(Model model,Play form,@PathVariable Long id,@PathVariable Long id_actor ) {
		System.out.println("POST Play_update ");

		Actor actor = ar.findById(id_actor).get();
		form.setActor(actor);

		form.setId(id); //IMPOSTA L'ID
		System.out.println("++"+form.toString());
		pr.save(form);
		System.out.println("-"+id);
		//System.out.println(update);

		return "redirect:/actor/plays/"+id_actor;  //redirect: vai a endpoint /film/
	}

	@GetMapping("/del_play/{id}")   // {id} è una path Variable
	public String deletePlay(@PathVariable Long id) {

		Play p = pr.findById(id).get();
		pr.delete(p);
		System.out.println("Cancellazione genere prima del redirect");
		return "redirect:/actor/plays/"+p.getActor().getId();
	}
}
