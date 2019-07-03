
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

import com.example.pgsql.model.Users;


/**
 * Classe pour la gestion des écritures des préférences dans les ontologies
 *
 * @date 30/06/2019
 *
 * @author Jerome Dh
 */
 public class WritePreference extends BaseWriter
 {

	/**
	 * Ontologie de préférences
	 *
	 * @var String 
	 */
    public static final String PREF_FILE = "O4UAD.owl";

	/**
	 * Contructeur
	 *
	 * @param Users person
	 * @param String prefs
	 */
	public WritePreference()
	{
	}

	//Exécuter l'action
	public void execute(Users person, String prefs)
	{
		//charger le model
		OntModel model = this.getModel(PREF_FILE);

		// == Les namespaces         
		model.setNsPrefix(PREFIX, NS);

		//Ajouter des personnes
		this.addPreferencePerson(model, person, prefs);

		this.writeInFile(model, PREF_FILE);

	}


	/**
	 * Ajouter les préférences d'une personne
	 *
	 * @param OntModel model - Le model
	 * @param Users person - La personne
	 * @param String prefs - chaine contenant les préférences
	 */
	protected void addPreferencePerson(OntModel model, Users person, String prefs)
	{
		StringTokenizer tokens = new StringTokenizer(prefs, ";");
		int taille = tokens.countTokens();

		if(taille > 0)
		{
			//Créer la classe mère "prefMere"
			OntClass prefMere = null;
			Property personP = null;
			Property deseaseTypeP = null;
		
			prefMere = model.getOntClass(CLASSE_PREF);
			if(prefMere == null)
			{
				prefMere = model.createClass(CLASSE_PREF);
			}

			//Créer les propriétés
			personP = model.getProperty(PERSON_PROPERTY);
			if(personP == null)
			{
				personP = model.createProperty(PERSON_PROPERTY);
			}

			deseaseTypeP = model.getProperty(DESEASE_PROPERTY);
			if(deseaseTypeP == null)
			{
				deseaseTypeP = model.createProperty(DESEASE_PROPERTY);
			}

			for(int i = 0; i < taille; ++i)
			{
				String pref = tokens.nextToken();

				if(LISTE_PREF.contains(pref))
				{
					this.addPreference(model, prefMere, personP, deseaseTypeP, pref, person.getEmail());
				}
			}
		}
	}

	/**
	 * Ajouter une sous-classe de "Preference"
	 *
	 * @param OntModel model - Le model
	 * @param OntClass classeMere - La classe mère
	 */
	protected void addPreference(OntModel modelPref, OntClass classeMere, Property personP, Property deseaseTypeP, String desease, String person)
	{
		int i = (int) (Math.random() * 10);
		String id = "Pref." + System.currentTimeMillis() + i;

		//Créer un objet
		OntClass prefClasse = modelPref.createClass(NS + id);

		//Ajout les propriétés et leurs valeurs
		prefClasse.addProperty(personP, modelPref.createResource(NS + person))
				  .addProperty(deseaseTypeP, modelPref.createResource(NS + desease));

		// ==== Les liens de parenté
		classeMere.addSubClass(prefClasse);

	}

 }