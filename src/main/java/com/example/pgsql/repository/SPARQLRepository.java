package com.example.pgsql.repository;

import java.util.List;
import java.util.ArrayList;

// The ARQ application API.
import org.apache.jena.atlas.io.IndentedWriter ;
import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.* ;
import org.apache.jena.vocabulary.DC ;
import org.apache.jena.util.FileManager;

import javax.servlet.http.HttpSession;

import com.example.pgsql.beans.*;
import com.example.pgsql.model.Users;


/**
 * Repository pour la gestion des réquêtes SPARQL 
 *
 */
public class SPARQLRepository
{
	/**
	* La session en cours
	* @var HttpSession session
	*/
	private HttpSession session;
	
	/**
	 * Constructor
	 */
	public SPARQLRepository(HttpSession session)
	{
		this.session = session;
	}

	/**
	 * Autocomplétion
	 */
	public List<AutoComplete> findForAutoComplete(String term)
	{
		List<AutoComplete> list = new ArrayList<AutoComplete>();

		// Réquêtes vers O4TSS
		O4TSSRDF basis1 = new O4TSSRDF(this.session);
		list.addAll(basis1.autoCompleteQuery(term));

		// Réquêtes vers O4UP
		O4UPRDF basis2 = new O4UPRDF(this.session);
		list.addAll(basis2.autoCompleteQuery(term));

		return list;

	}

	/**
	 * Full Search
	 */
	public JSONResponse fullSearch(String term)
	{
		
		// Concevoir la réponse
		JSONResponse jsonResponse = new JSONResponse();

		// Fouiller dans 04TSS
		O4TSSRDF basis1 = new O4TSSRDF(this.session);
		jsonResponse.addDiseases(basis1.fullQuery(term));

		// Fouiller dans O4UP
		O4UPRDF basis2 = new O4UPRDF(this.session);
		jsonResponse.addDiseases(basis2.fullQuery(term));

		return jsonResponse;

	}
	
	/**
	 * Authentifier un user
	 */
	public Users getUser(String login, String password)
	{
		O4UPRDF o4TSS = new O4UPRDF(session);
		Users user = o4TSS.getUser(login, password);

		return user;
	}

}