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

public class SPARQLRepository
{
	public SPARQLRepository()
	{

	}

	public List<AutoComplete> findForAutoComplete(String term)
	{
		List<AutoComplete> list = new ArrayList<AutoComplete>();

		// Réquêtes vers O4TSS
		O4TSSRDF basis1 = new O4TSSRDF();
		list = basis1.autoCompleteQuery(list, term);

		// Réquêtes vers O4UP
		O4UPRDF basis2 = new O4UPRDF();
		list = basis2.autoCompleteQuery(list, term);

		//
		// ==
		//

		return list;

	}

	public JSONResponse fullSearch(String term)
	{
		//Capturer le temps au début
		long t0, t1;
            t0 = System.currentTimeMillis();

		System.out.println("\n+-------------------------------------------+");
		System.out.println("+----------- Début d'exécution -------------+");
		System.out.println("+-------------------------------------------+\n");
		System.out.println("+------- Query Onto -------------+\n");

		// Concevoir la réponse
		JSONResponse jsonResponse = new JSONResponse();

		// Fouiller dans 04TSS
		O4TSSRDF basis1 = new O4TSSRDF();
		jsonResponse = basis1.fullQuery(jsonResponse, term);

		// Fouiller dans O4UP
		O4UPRDF basis2 = new O4UPRDF();
		jsonResponse = basis2.fullQuery(jsonResponse, term);

		//
		// ==
		//

		//Calcul du temps d'exécution
		t1 = System.currentTimeMillis();
		System.out.println("\n+-------------------------------------------+\n");
        System.out.println("\tTemps d'exécution: "+ (t1 - t0) + " ms");
		System.out.println("+\n-------------------------------------------+\n");

		
		return jsonResponse;

	}
	
	/**
	 * Trouver une réponse à la réquete
	 */
    public JSONResponse findByQuestion(String question)
	{
		//Résultats
		JSONResponse jsonResponse = new JSONResponse();

		//Le model
		Model model = FileManager.get().loadModel("data/sparql-example.ttl");

        String queryString = "SELECT ?p ?name ?prenom " +
                "WHERE { " +
                "?p <http://xmlns.com/foaf/0.1/name> ?name ." +
                "?p <http://xmlns.com/foaf/0.1/firstname> ?prenom ." +
                "FILTER REGEX(?prenom, \"(" +  question + ")\") " +
                "}";

        Query query = QueryFactory.create(queryString);
     
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

			Person person;
            ResultSet results = qexec.execSelect();

            for(; results.hasNext() ; ) {

                QuerySolution soln = results.nextSolution();
                //Resource p = soln.getResource("p");
                //Resource valeur = soln.getResource("valeur");
                // RDFNode x = soln.get("varName")
                // Literal l = soln.getLiteral("varL")
                //System.out.println(" Mot : - " + valeur.toString());
				RDFNode y = soln.get("p") ;
				RDFNode x = soln.get("name") ;
				RDFNode z = soln.get("prenom") ;

                System.out.println("    " + y.toString()) ;
                System.out.println("    " + x.toString()) ;
                System.out.println("    " + z.toString()) ;

				//Ajouter à la réponse
				jsonResponse.addPerson(new Person(x.toString(), z.toString(), ""));

                // Check the type of the result value
                if ( x instanceof Literal )
                {
                    Literal titleStr = (Literal)x  ;
                    System.out.println("    "+titleStr) ;
                }
                else
                    System.out.println("Strange - not a literal: "+x) ;

            }
        }

		return jsonResponse;
	}
}