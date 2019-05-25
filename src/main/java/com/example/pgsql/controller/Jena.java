package com.example.pgsql.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.validation.Valid;

import com.example.pgsql.repository.*;
import com.example.pgsql.beans.*;

@RestController
public class Jena 
{

	/**
	 * Auto-complétetion
	 */
	@CrossOrigin()
	@GetMapping("autocomplete")
	public List<AutoComplete> autocomplete(
		@RequestParam(required=false, defaultValue="") String term)
	{

		//SPARQLRepository repository = new SPARQLRepository();

		SPARQLThreadRepository repository = new SPARQLThreadRepository();
		return repository.findForAutoComplete(term);

	}

	/**
	 * Recherche complète (Full)
	 */
	@CrossOrigin()
	@GetMapping("fullsearch")
	public JSONResponse fullsearch(
		@RequestParam(required=false, defaultValue="") String term,
		@RequestParam(required=false, defaultValue="1") String thread
		)
	{
		//Temps début
		long t0 = debutExecution();

		JSONResponse jsonresponse;
		if(thread.equals("1"))
		{
			System.out.println("\t\t Execution multithread \n\n");
			SPARQLThreadRepository repository = new SPARQLThreadRepository();
			jsonresponse = repository.fullSearch(term);
		}
		else
		{
			System.out.println("\t\t Execution monothread \n\n");
			SPARQLRepository repository = new SPARQLRepository();
			jsonresponse = repository.fullSearch(term);
		}

		//Fin d'exécution
		finExecution(t0);

		return jsonresponse;	

	}
	
	/**
	 * Début de l'exécution
	 */
	private long debutExecution()
	{
		//Capturer le temps au début

		System.out.println("\n+-------------------------------------------+");
		System.out.println("\t\t Début d'exécution ");
		System.out.println("+-------------------------------------------+\n\n");
		System.out.println("\t\t Réquête en cours \n\n");

		return System.currentTimeMillis();

	}
	
	/**
	 * Fin de l'exécution
	 */
	private void finExecution(long t0)
	{
		// == Calcul du temps d'exécution
		long t1 = System.currentTimeMillis();

		System.out.println("\n+-------------------------------------------+\n");
        System.out.println("\t\tTemps d'exécution: "+ (t1 - t0) + " ms");
		System.out.println("\n-------------------------------------------+\n");

	}
}


