package com.example.pgsql.repository;

import java.util.List;
import java.util.ArrayList;

// The ARQ application API.
import org.apache.jena.atlas.io.IndentedWriter ;
import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.* ;
import org.apache.jena.vocabulary.DC ;
import org.apache.jena.util.FileManager;

import com.example.pgsql.beans.*;
import com.example.pgsql.model.Users;


/**
 * Repository pour la gestion des réquêtes SPARQL 
 *
 */
public class SPARQLRepository
{
	/**
	 * Constructor
	 */
	public SPARQLRepository()
	{

	}

	/**
	 * Gestion de l'autocomplétion
	 */
	public List<AutoComplete> findForAutoComplete(String term)
	{
		List<AutoComplete> list = new ArrayList<AutoComplete>();

		// Réquêtes vers O4TSS
		O4TSSRDF basis1 = new O4TSSRDF();
		list.addAll(basis1.autoCompleteQuery(term));

		// Réquêtes vers O4UP
		O4UPRDF basis2 = new O4UPRDF();
		list.addAll(basis2.autoCompleteQuery(term));

		return list;

	}

	/**
	 * Gestion des réquêtes à réponses complètes
	 */
	public JSONResponse fullSearch(String term)
	{
		
		// Concevoir la réponse
		JSONResponse jsonResponse = new JSONResponse();

		// Fouiller dans 04TSS
		O4TSSRDF basis1 = new O4TSSRDF();
		jsonResponse.addDiseases(basis1.fullQuery(term));

		// Fouiller dans O4UP
		O4UPRDF basis2 = new O4UPRDF();
		jsonResponse.addDiseases(basis2.fullQuery(term));

		return jsonResponse;

	}
	
	/**
	 * Authentifier un user
	 */
	public Users getUser(String login, String password)
	{
		O4UPRDF o4TSS = new O4UPRDF();
		Users user = o4TSS.getUser(login, password);

		return user;
	}

}