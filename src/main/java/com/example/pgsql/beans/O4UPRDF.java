package com.example.pgsql.beans;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.RDF;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.example.pgsql.model.Users;

/**
 * Réquête sur l'ontologie O4UPRDF.owl
 */
 
public class O4UPRDF extends BaseOnto
{

	/**
	 * @return filename - string
	 */
	private static final String FILE_NAME = "O4UPRDF.owl";

	/**
	 * La session en cours
	 *
	 * @var HttpSession
	 */
	private HttpSession session;

	/**
	 * Constructor
	 *
	 * @param HttpSession session
	 */
	public O4UPRDF(HttpSession session)
	{
		super(FILE_NAME);
		
		this.session = session;

	}
	
   	/**
	 * Lancer une Full search
	 *
	 */
	public List<Desease> fullQuery(String term)
	{
		term = construireRegex(term);
		/* System.out.println("+\nLe term: " + term + "\n"); */

		List<Desease> response;

        response = showFullQuery(
					"select distinct ?label ?description" +
					"	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:subClassOf pref:Personne ." +
								"?classe vcard:Given ?label ." +
								"OPTIONAL { " +
									"?classe vcard:TITLE ?description ." +
								"} "+
							"} " +							
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return response;
		
    }
	
	/**
	 * Rechercher les titres pour l'autocompletion
	 */
	public List<AutoComplete> autoCompleteQuery(String term)
	{
		term = construireRegex(term);

		List<AutoComplete> list;

        list = showAutoCompleteQuery(
                  "select distinct ?label ?description" +
					"	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:subClassOf pref:Personne ." +
								"?classe vcard:Given ?label ." +
								"OPTIONAL { " +
									"?classe vcard:TITLE ?description ." +
								"} "+
							"} " +				
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return list;

    }

	public Users getUser(String login, String password) 
	{
		Users user = null;

		user = getUniqueUser(
					"select distinct ?name ?firstName ?email " +
					" ?adresse ?tel ?country ?title ?password " +
					" ?sexe " +
					" 	where " + 
						"{ " +
							" { " +
								"?classe a owl:Class ." +
								"?classe rdfs:subClassOf pref:Personne ." +
								"?classe vcard:EMAIL ?email ." +
								"?classe vcard:KEY ?password ." +
								"OPTIONAL { " +
									"?classe vcard:FN ?name ." +
									"?classe vcard:Family ?firstName ." +
									"?classe vcard:Country ?country ." +
									"?classe vcard:TEL ?tel ." +
									"?classe vcard:ADR ?adresse ." +
									"?classe vcard:TITLE ?title ." +
									"?classe vcard:Other ?sexe ." +
								"} " +
							"} " +						
							"FILTER REGEX(?email, \"" + login + "\", \"i\") ." +
						"} " +
						"LIMIT 1");
		
		
		return user;
		
	}
	
	/** 
	 * Utiliser le reasoner ou pas 
	 *
	 * @return boolean
	 */
	public boolean useReasoner()
	{
		String status = (String)this.session.getAttribute("reasoner");

		return (status != null && status.equals("active"));

	}

}
