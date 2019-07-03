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

import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

import com.example.pgsql.model.Users;


/**
 * Réquête sur l'ontologie O4TSSRDF.owl
 */
 
public class O4TSSRDF extends BaseOnto
{
	/**
	 * La session en cours
	 *
	 * @var HttpSession
	 */
	private HttpSession session;
	
	/**
	 * @var string
	 */
	private static final String FILE_NAME = "O4TSSRDF.owl";
	
	/**
	 * Ontologie de préférences
	 *
	 * @var String 
	 */
    public static final String PREF_FILE = "O4UAD.owl";
	
	/**
	 * Constructeur
	 *
	 * @param HttpSession session
	 */
	public O4TSSRDF(HttpSession session)
	{
		super(FILE_NAME);
		
		this.session = session;

	}
	
	/**
	 * Trouver les préférences de l'utilisateur connecté
	 *
	 * @return String
	 */
	public String getPreferences()
	{
		String regexPrefs = "";

		if(this.session.getAttribute("user") != null)
		{
			Users user = (Users)session.getAttribute("user");

			this.setOntModel(PREF_FILE);

			List<String> prefs = this.getPreferencesUser(
				"select distinct ?deseaseType " +
				   "	where " +
						"{ " +
							"?classe a owl:Class ." +
							"?classe rdfs:subClassOf pref:Preference ." +
							"?classe pref:deseaseType ?deseaseType ." +
							"?classe pref:person <"  + PREF_NS + user.getEmail() + "> ." +

						"} ");

			if(prefs.size() > 0)
			{
				String ch = prefs.get(0);
				String t = "";
				t = " { " +
					" ?classe rdfs:type <" + ch + "> .";
				for(int i = 1; i < prefs.size(); ++i)
				{
					//Extraire le terme
					ch = prefs.get(i);

					t += " OPTIONAL {  ?classe rdfs:type " + ch + " . } ";		
				}
				t += " } ";
				regexPrefs = t;
			}
		}

		return regexPrefs;

	}

   	/**
	 * Faire une Full search
	 *
	 */
	public List<Desease> fullQuery(String term)
	{

		String regexPrefs = this.getPreferences();

		term = construireRegex(term);

		List<Desease> response;

		this.setOntModel(FILE_NAME);

        response = showFullQuery(
                   "select distinct ?label ?description" +
				   "	where " +
						"{ " +
							"?classe a owl:Class ." +
							"?classe rdfs:subClassOf pref:Desease ." +
							"{ " +
								"?classe rdfs:label ?label ." +
								"?classe rdfs:description ?description ." +
							"} " +							

							regexPrefs +								

							"FILTER REGEX(?description, \"" + term + "\", \"i\") ." +
						"} " +

						"LIMIT 20");

		return response;

    }

	/**
	 * Faire une recherche des titres pour l'autocompletion
	 */
	public List<AutoComplete> autoCompleteQuery(String term)
	{
		String regexPrefs = this.getPreferences();

		term = construireRegex(term);
		/* System.out.println("+\nLe term: " + term + "\n"); */

		List<AutoComplete> list;

		this.setOntModel(FILE_NAME);

        list = showAutoCompleteQuery(
                   "select distinct ?label ?description" +
				   "	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:subClassOf pref:Desease ." +
								"?classe rdfs:label ?label ." +

								regexPrefs +
								
								"OPTIONAL { " +
									"?classe rdfs:description ?description ." +
								"} "+

							"} " +							
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return list;

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
