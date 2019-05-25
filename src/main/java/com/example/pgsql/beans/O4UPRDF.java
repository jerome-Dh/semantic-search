package com.example.pgsql.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * Réquête sur l'ontologie O4UPRDF.owl
 */
 
public class O4UPRDF extends BaseOnto
{

   	/**
	 * Lancer une Full search
	 *
	 */
	public List<Disease> fullQuery(String term)
	{
		term = construireRegex(term);
		/* System.out.println("+\nLe term: " + term + "\n"); */

		OntModel m = getModel("O4UPRDF.owl");

		List<Disease> response;

        response = showFullQuery(m,
					getPrefix() +
					"select distinct ?classe ?label ?comment ?genre ?lienWiki" +
					"	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:label ?label ." +
								"OPTIONAL { " +
									"?classe ubis:identifier ?comment ." +
								"} "+
								"OPTIONAL { " +
									"?classe ubis:category ?genre ." +
								"} "+
								"OPTIONAL { " +		
									"?classe ubis:image ?lienWiki ." +
								"} " +
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

		OntModel m = getModel("O4UPRDF.owl");

		List<AutoComplete> list;

        list = showAutoCompleteQuery(m,
                   getPrefix() +
                   "select distinct ?label ?genre" +
				   "	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:label ?label ." +
								"OPTIONAL { " +
									"?classe ubis:category ?genre ." +
								"} "+
							"} " +							
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return list;

    }

}
