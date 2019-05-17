package com.example.pgsql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;

@Controller
public class MainController 
{

	@GetMapping("/")
	public String accueil()
	{
		return "index";
	}

	@GetMapping("/serp")
	public String recherche()
	{
		return "serp";
	}

}

