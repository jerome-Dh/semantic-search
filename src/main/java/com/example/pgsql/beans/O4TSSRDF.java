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


/**
 * Réquête sur l'ontologie O4TSSRDF.owl
 */
 
public class O4TSSRDF extends BaseOnto
{
	/**
	 * @return string
	 */
	private static final String FILE_NAME = "O4TSSRDF.owl";
	
	public O4TSSRDF()
	{
		super(FILE_NAME);
	}

   	/**
	 * Faire une Full search
	 *
	 */
	public List<Disease> fullQuery(String term)
	{
		term = construireRegex(term);
		/* System.out.println("+\nLe term: " + term + "\n"); */

		List<Disease> response;

        response = showFullQuery(
                   "select distinct ?classe ?label ?comment ?genre ?lienWiki" +
				   "	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:label ?label ." +
								"OPTIONAL { " +
									"?classe obo:IAO_0000115 ?comment ." +
								"} "+
								"OPTIONAL { " +
									"?classe oboInOwl:hasOBONamespace ?genre ." +
								"} "+
								"OPTIONAL { " +		
									"?axiome a owl:Axiom . " +
									"?axiome oboInOwl:hasDbXref ?lienWiki ." +
									"?axiome owl:annotatedSource ?classe ." +
								"} " +
							"} " +							
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return response;

    }

	/**
	 * Faire une recherche des titres pour l'autocompletion
	 */
	public List<AutoComplete> autoCompleteQuery(String term)
	{
		term = construireRegex(term);

		List<AutoComplete> list;

        list = showAutoCompleteQuery(
                   "select distinct ?label ?genre" +
				   "	where " +
						"{ " +
							"{ " +
								"?classe a owl:Class ." +
								"?classe rdfs:label ?label ." +
								"OPTIONAL { " +
									"?classe oboInOwl:hasOBONamespace ?genre ." +
								"} "+
							"} " +							
							"FILTER REGEX(?label, \"" + term + "\", \"i\") ." +
						"} " +
						"LIMIT 20");

		return list;

    }

}
