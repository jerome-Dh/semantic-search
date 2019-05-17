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


public class O4TSSRDF extends BaseOnto
{

   	/**
	 * Lancer une Full search
	 *
	 */
	public JSONResponse fullQuery(JSONResponse jsonResponse, String term)
	{
		term = construireRegex(term);
		System.out.println("+\nLe term: " + term + "\n");

		OntModel m = getModel("O4TSSRDF.owl");
        jsonResponse = showFullQuery(jsonResponse, m,
                   getPrefix() +
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

		return jsonResponse;
		
    }
	
	/**
	 * Rechercher pour l'autocompletion
	 */
	public List<AutoComplete> autoCompleteQuery(List<AutoComplete> list, String term)
	{
		term = construireRegex(term);

		OntModel m = getModel("O4TSSRDF.owl");
        list = showAutoCompleteQuery(list, m,
                   getPrefix() +
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
