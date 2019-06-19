package com.example.pgsql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

/**
 * Controlleur principal du site
 */

@Controller
public class MainController 
{

	//Accueil
	@GetMapping("/")
	public String accueil()
	{
		return "index";
	}

	@GetMapping("/logout")
    public String logout(HttpSession session) 
	{
        session.invalidate();

        return "redirect:/";

	}

	//Affichage des résultats
	@GetMapping("/serp")
	public String serp()
	{
		return "serp";
	}

	//Aide
	@GetMapping("/aide")
	public String aide()
	{
		return "aide";
	}

	//Policies
	@GetMapping("/policies")
	public String policies()
	{
		return "policies";
	}

	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}

	@GetMapping("/error")
	public String error()
	{
		return "error";
	}

}

