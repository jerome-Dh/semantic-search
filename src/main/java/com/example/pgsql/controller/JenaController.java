package com.example.pgsql.controller;

import java.util.*;
import java.time.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.pgsql.repository.*;
import com.example.pgsql.beans.*;

/**
 * Traiter les réquêtes SPARQL avec l'api Jena
 * 
 * @author Jerome Dh
 * 
 * @date Mai 2019
 *
 */
@RestController
public class JenaController
{
	
	/**
	 * Activé/Désactiver le reasoner
	 */
	//@CrossOrigin()
	@GetMapping("/reasoner")
	public String reasonerml(
		HttpSession httpSession,
		@RequestParam(required=true) String term
		)
	{

		//Sauvegarder en session
		httpSession.setAttribute("reasoner", term);
		
		System.out.println("Statut du reasoner: " + term);

		return "ok";

	}
		

	/**
	 * Auto-complétetion
	 */
	//@CrossOrigin()
	@GetMapping("autocomplete")
	public List<AutoComplete> autocomplete(
		HttpSession httpSession,
		@RequestParam(required=false, defaultValue="") String term)
	{

		//SPARQLRepository repository = new SPARQLRepository(httpSession);

		SPARQLThreadRepository repository = new SPARQLThreadRepository(httpSession);

		return repository.findForAutoComplete(term);

	}

	/**
	 * Recherche complète (Full)
	 */
	//@CrossOrigin()
	@GetMapping("fullsearch")
	public JSONResponse fullsearch(
		HttpSession httpSession,
		@RequestParam(required=false, defaultValue="") String term,
		@RequestParam(required=false, defaultValue="1") String thread
		)
	{
		//Temps début
		long t0 = debutExecution();

		JSONResponse jsonresponse;
		if(thread.equals("1"))
		{
			log("\t\t Execution multithread \n\n");
			SPARQLThreadRepository repository = new SPARQLThreadRepository(httpSession);
			jsonresponse = repository.fullSearch(term);
		}
		else
		{
			log("\t\t Execution monothread \n\n");
			SPARQLRepository repository = new SPARQLRepository(httpSession);
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
		// == Capturer le temps au début

		log("\n+-------------------------------------------+");
		log("\t\t Début d'exécution ");
		log("+-------------------------------------------+\n\n");
		log("\t\t Réquête en cours \n\n");

		return System.currentTimeMillis();

	}
	
	@GetMapping("tester")
	public String tester(HttpSession httpSession)
	{
		
		return "Bienvenue";

	}
		
	
	/**
	 * Fin de l'exécution
	 */
	private void finExecution(long t0)
	{
		// == Calcul du temps d'exécution
		long t1 = System.currentTimeMillis();

		log("\n+-------------------------------------------+\n");
        log("\t\tTemps d'exécution: "+ (t1 - t0) + " ms");
		log("\n-------------------------------------------+\n");

	}
	
	/**
	 * Impression
	 */
	private void log(String texte)
	{
		System.out.println(texte);
	}

}


