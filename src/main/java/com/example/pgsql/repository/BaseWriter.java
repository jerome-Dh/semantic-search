
package com.example.pgsql.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.RDFS ;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.*;
import org.apache.jena.sparql.util.Context;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.datatypes.xsd.*;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.impl.XSDDouble;
import org.apache.jena.datatypes.xsd.impl.*;
import org.apache.jena.util.*;
import org.apache.jena.ontology.*;


/**
 * Classe de base pour la gestion des écritures dans les ontologies
 *
 * @date 30/06/2019
 *
 * @author Jerome Dh
 */
 public abstract class BaseWriter
 {
	/**
	 * Répertoire des données rdf, owl, ..
	 *
	 * @var String 
	 */
    public static final String SOURCE = "./data/";

	/**
	 * Le namespace global
	 *
	 * @var String 
	 */
    public static final String NS = "http://uy1/m1/groupe22#";
	
	/**
	 * Le préfixe
	 *
	 * @var String 
	 */
    public static final String PREFIX = "pref";	

	/**
	 * Classe "Personne"
	 *
	 * @var String 
	 */
    public static final String CLASSE_PERSON = NS + "Personne";
	
	/**
	 * Classe "Desease"
	 *
	 * @var String 
	 */
    public static final String CLASSE_DESEASE = NS + "Desease";
	
	/**
	 * Classe "Preference"
	 *
	 * @var String 
	 */
    public static final String CLASSE_PREF = NS + "Preference";
	
	/**
	 * property person 
	 *
	 * @var String 
	 */
    public static final String PERSON_PROPERTY = NS + "person";
	
	/**
	 * property deseaseType
	 *
	 * @var String 
	 */
    public static final String DESEASE_PROPERTY = NS + "deseaseType";

	/**
	 * Liste des préférences
	 *
	 * @var List 
	 */
    public static final List<String> LISTE_PREF 
		= Arrays.asList(new String[]{"Contagieuse", 
			"Cardiovasculaire", 
			"Cancer", 
			"Respiratoire", 
			"Dermatologiques",
			"Rare",
			"Genetique",
			"Digestive"
		});


	/**
	 * Contructeur
	 *
	 */
	public BaseWriter()
	{
	}

	/**
	 * Ecrire des données dans le fichier 
	 *
	 * @param Model model - Le model
	 * @param String fileName - Le nom du fichier
	 */
    protected void writeInFile(Model model, String fileName) 
	{

        // Ajout des propriétés à la racine
        Map<String, Object> properties = new HashMap<>() ;
        properties.put("showXmlDeclaration", "true");
		properties.put("showDoctypeDeclaration", "true");

        // Mettre les propriétés dans un Context.
        Context cxt = new Context();
        cxt.set(SysRIOT.sysRdfWriterProperties, properties);

		try
		{
			String chemin = ".\\src\\main\\resources\\data\\" + fileName;

			FileOutputStream fOS = 
				new FileOutputStream(new File(chemin));

			RDFWriter.create()
            .format(RDFFormat.RDFXML_ABBREV)
            .context(cxt)
            .source(model)
            .output(fOS);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}

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