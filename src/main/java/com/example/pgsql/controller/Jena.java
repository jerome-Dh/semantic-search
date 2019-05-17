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
import org.springframework.web.bind.annotation.CrossOrigin;;
import javax.validation.Valid;

// The ARQ application API.
import org.apache.jena.atlas.io.IndentedWriter ;
import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.* ;
import org.apache.jena.vocabulary.DC ;
import org.apache.jena.util.FileManager;

import com.example.pgsql.repository.*;
import com.example.pgsql.beans.*;

@RestController
public class Jena 
{
   
	//@CrossOrigin(origins = "http://localhost:9000")
	@CrossOrigin()
	@GetMapping("testAjax")
	public JSONResponse testAjax(@RequestParam(required=false, defaultValue="") String term)
	{
		long t0, t1;
            t0 = System.currentTimeMillis();

		SPARQLRepository repository = new SPARQLRepository();

		t1 = System.currentTimeMillis();
        System.out.println("Temps d'exécution = "+(t1 - t0));

		return repository.findByQuestion(term);
	}
	
	
	@CrossOrigin()
	@GetMapping("autocomplete")
	public List<AutoComplete> autocomplete(@RequestParam(required=false, defaultValue="") String term)
	{

		SPARQLRepository repository = new SPARQLRepository();

		return repository.findForAutoComplete(term);

	}
	
	@CrossOrigin()
	@GetMapping("fullsearch")
	public JSONResponse fullsearch(@RequestParam(required=false, defaultValue="") String term)
	{
		SPARQLRepository repository = new SPARQLRepository();

		return repository.fullSearch(term);

	}
	

}


