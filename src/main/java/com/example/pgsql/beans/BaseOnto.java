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


public class BaseOnto {

	// Répertoire des données rdf, owl, ..
    public static final String SOURCE = "./data/";

    // les namespaces des ontologies
    public static final String PIZZA_NS = "http://www.co-ode.org/ontologies/pizza/pizza.owl#";
	public static final String BFO_NS = "http://www.ifomis.org/bfo/1.1#";
    public static final String OWL_NS = "http://www.w3.org/2002/07/owl#";
    public static final String TRANS_NS = "http://purl.org/obo/owl/TRANS#";
    public static final String OBO_NS = "http://purl.obolibrary.org/obo/";
    public static final String DC_NS = "http://purl.org/dc/elements/1.1/";
    public static final String MUO_NS = "http://purl.oclc.org/NET/muo/muo#";
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
    public static final String SNAP_NS = "http://www.ifomis.org/bfo/1.1/snap#";
    public static final String SPAN_NS = "http://www.ifomis.org/bfo/1.1/span#";
    public static final String SKOS_NS = "http://www.w3.org/2004/02/skos/core#";
    public static final String RO_NS = "http://www.obofoundry.org/ro/ro.owl#";
    public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    public static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String ACE_NS = "http://attempto.ifi.uzh.ch/ace_lexicon#";
    public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String OBOINOWL_NS = "http://www.geneontology.org/formats/oboInOwl#";
    public static final String UBIS_NS = "http://ubisworld.org/documents/ubis.rdf#";

	// Les préfixes des réquêtes
	protected String getPrefix()
	{
		return "prefix bfo: <" + BFO_NS + ">\n" +
					"prefix rdfs: <" + RDFS.getURI() + ">\n" +
					"prefix oboInOwl: <" + OBOINOWL_NS + ">\n" +
					"prefix obo: <" + OBO_NS + ">\n" +
					"prefix ubis: <" + UBIS_NS + ">\n" +
					"prefix owl: <" + OWL.getURI() + ">\n";
	}

	/**
	 * Construire la chaine de réquête (REGEX)
	 */
	protected String construireRegex(String term)
	{
		term = term.trim();

		StringTokenizer token = new StringTokenizer(term, " ");
		int taille = token.countTokens();
		
		String convert = "(" + term + ")";
		for(int i=0; i < taille; ++i)
		{
			String mot = token.nextToken().trim();
			convert += "|(" + mot + ")";
		}

		return convert;

	}

	/**
	 * Afficher le résultat de l'autocompletion
	 */
	protected List<AutoComplete> showAutoCompleteQuery( Model m, String q ) 
	{
		List<AutoComplete> list = new ArrayList<AutoComplete>();

		Query query = QueryFactory.create( q );
        QueryExecution qexec = QueryExecutionFactory.create( query, m );
        
		try 
		{
            ResultSet results = qexec.execSelect();

			//Mes résultats
			/* System.out.println("Les résultats");
			System.out.println("+----------------------+----------------------+-------------------+");
			*/
			int i = 0;
			for ( ; results.hasNext() ; )
			{
				QuerySolution soln = results.nextSolution();

				String	label = getLiteral(soln, "label");
				String	genre = getLiteral(soln, "genre"); 

				// Afficher le résultat retourné en console
				/* System.out.println(
					++i + ") " + 
					label + " -+- " +
					genre + " -+- " 
					); */
	
				list.add(new AutoComplete(label, genre));
			}
        }
        finally 
		{
            qexec.close();
        }

		return list;

    }

	/**
	 * Afficher le résultat FullQuery
	 */
	protected List<Disease> showFullQuery( Model m, String q ) 
	{
		List<Disease> diseases = new ArrayList<Disease>();

		Query query = QueryFactory.create( q );
        QueryExecution qexec = QueryExecutionFactory.create( query, m );

		try 
		{
            ResultSet results = qexec.execSelect();

			//Mes résultats
			/* System.out.println("Les résultats");
			System.out.println("+----------------------+----------------------+-------------------+");
			*/
			int i = 0;
			for ( ; results.hasNext() ; )
			{
				QuerySolution soln = results.nextSolution();

				String classe = getLiteral(soln, "classe");
				String label = getLiteral(soln, "label");
				String comment = getLiteral(soln, "comment");
				String genre = getLiteral(soln, "genre"); 
				String lienWiki = getLiteral(soln, "lienWiki");

				// Tester le type de résultat retourné
				/* System.out.println(
					++i + ") " + 
					classe + " -+- " + 
					label + " -+- " + 
					comment + " -+- " + 
					genre + " -+- " +
					lienWiki
					); */
					
				diseases.add(new Disease(label, comment, genre, lienWiki));

			}
        }
        finally {
            qexec.close();
        }

		return diseases;

    }
	
	/**
	 * Obtenir un donnée littérale dans un résultat de réquête
	 */
	protected String getLiteral(QuerySolution soln, String champs)
	{
		//Récupérer le noeud correspondant au champs
		RDFNode rdfNode = soln.get(champs);
		
		if(rdfNode == null)
			return "";

		String ret = "";
		if ( rdfNode.isLiteral() )
		{
			ret = ((Literal)rdfNode).getLexicalForm() ;
		}
		if ( rdfNode.isResource() )
		{
			Resource r = (Resource)rdfNode ;
			if ( ! r.isAnon() )
			{
				ret = r.getURI();
			}
		}
		
		return ret;

	}

	/**
	 * Créer un sur l'ontologie chargée
	 */
	protected OntModel getModel(String nom_fichier) 
	{
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		loadData( m, nom_fichier );
		return m;
    }

	/**
	 * Charger une ontologie
	 */
    protected void loadData( Model m, String nom_fichier ) {
        FileManager.get().readModel( m, SOURCE + nom_fichier );
    }

}
