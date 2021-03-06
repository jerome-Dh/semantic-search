package com.example.pgsql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;

import com.example.pgsql.exception.ResourceNotFoundException;
import com.example.pgsql.model.Users;
import com.example.pgsql.repository.UserRepository;
import com.example.pgsql.repository.SPARQLRepository;
import com.example.pgsql.repository.UserRDFRepository;
import com.example.pgsql.beans.Login;


@Controller
public class UserController 
{

    @Autowired
    private UserRepository userRepository; 

	/**
	 * Log in d'un User
	 */
	@ResponseBody
    @GetMapping("/login")
    public Login getUser(
		HttpSession session,
		@RequestParam(required=true) String email,
		@RequestParam(required=true) String password
		)
	{
		boolean status = false;
		String comment = "";
		
		Users user = null;
		
		//Chercher dans la BD
		//user = userRepository.findByEmail(email);
		
		//Chercher dans l'ontologie
		SPARQLRepository sparqlRepository = new SPARQLRepository(session);
		user = sparqlRepository.getUser(email, password);
		
		if(user != null)
		{
			if(user.getPassword().equals(password))
			{
				status = true;
				comment = "Connecté avec succès !";
				
				session.setAttribute("user", user);

				System.out.println("Email: " + email + " Password: " + password);
			}
			else
			{
				comment = "Mot de passe invalide !";
			}
		}
		else
		{
			comment = "Données introuvables, veuillez réessayer !";
		}
	
		System.out.println(comment);

        return new Login(status, user, comment);

    }

	/**
	 * Création d'un User
	 */
	@ResponseBody
    @PostMapping("/user")
    public Login createUser(@RequestBody Users user, HttpSession session) 
	{
		//Enregistrer en BD 
		//user = userRepository.save(user);
		
		//Enregistrer dans l'ontologie
		UserRDFRepository userRDFRepository = new UserRDFRepository();
		userRDFRepository.create(user);

		boolean status = true;

		String comment = "";

		session.setAttribute("user", user);

		System.out.println(user);

		return new Login(status, user, comment);

	}
	
	/**
	 * Mise à jour d'un User
	 */
	@ResponseBody
	@PostMapping("/update")
    public Login updateUser(@RequestBody Users user, HttpSession session) 
	{
		//Modifier l'id du user
		Users userSession = (Users)session.getAttribute("user");
		user.setId(userSession.getId());

		//Sauvegarder
		//user = userRepository.update(user);

		boolean status = true;
		
		String comment = "";

		session.setAttribute("user", user);

		System.out.println(user);

		return new Login(status, user, comment);

	}

	/**
	 * Gestion du compte d'un user
	 */
	@GetMapping("/account")
    public String updateUser(HttpSession session) 
	{

		if(session.getAttribute("user") != null)
			return "account";
		else
			return "redirect:/";

	}
	
	@GetMapping("/inscription")
	public String inscription(HttpSession session)
	{
		if(session.getAttribute("user") != null)
			return "redirect:/account";
		else
			return "inscription";
	}
	
	/**
	 * Formulaire de création des préférences
	 */
	@GetMapping("/preferences")
	public String preferences(HttpSession session)
	{
		if(session.getAttribute("user") != null)
			return "preferences";
		else
			return "redirect:/inscription";
	}
	
	/**
	 * Enregistrer les préférences d'un user
	 */
	@ResponseBody
    @GetMapping("/savepreferences")
    public Login createPreferences(
		@RequestParam(required=true) String prefs,
		HttpSession session) 
	{
		Users user = null;
		boolean status = false;
		String comment = "Echec d'enregistrement";
		
		if(session.getAttribute("user") != null)
		{
			user = (Users)session.getAttribute("user");

			//Enregistrer dans l'ontologie
			UserRDFRepository userRDFRepository = new UserRDFRepository();
			userRDFRepository.addPreferences(user, prefs);

			status = true;
			comment = "Enregistrer avec succès";		

		}
		
		return new Login(status, user, comment);
	}

}