
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
 * Classe pour la gestion des écritures des users dans les ontologies
 *
 * @date 30/06/2019
 *
 * @author Jerome Dh
 */
 public class WritePerson extends BaseWriter
 {

	/**
	 * Ontologie de personnes
	 *
	 * @var String 
	 */
    public static final String PERSON_FILE = "O4UPRDF.owl";

	/**
	 * Constructeur
	 *
	 * @param Users person
	 */
	public WritePerson()
	{
		
	}

	//Exécuter l'action
	public void execute(Users person)
	{
		//charger le model
		OntModel model = this.getModel(PERSON_FILE);

		// == Les namespaces         
		model.setNsPrefix(PREFIX, NS);

		//Ajouter personne
		this.ajouterPersonne(model, person);

		this.writeInFile(model, PERSON_FILE);

	}
	

	/**
	 * Ajouter une personne
	 *
	 * @param OntModel model - Le model
	 * @param Users person - La personne
	 */
	protected void ajouterPersonne(OntModel model, Users person)
	{
		//Créer la classe mère "Person"
		OntClass personMere = null;

		personMere = model.getOntClass(CLASSE_PERSON);
		if(personMere == null)
		{
			personMere = model.createClass(CLASSE_PERSON);
		}

		this.addPersonne(model, personMere, person);

	}

	/**
	 * Ajouter les propriétés de la personne
	 *
	 * @param OntModel model - Le model
	 * @param OntClass classeMere - La classe mère
	 * @param Users person - La personne
	 */
	protected void addPersonne(OntModel model, OntClass classeMere, Users person)
	{
		String id = person.getEmail();

		//Créer un objet
		OntClass personClasse = model.createClass(NS + id);

		//Ajout les propriétés et leurs valeurs
		personClasse.addProperty(VCARD.Given, person.getFullName())
					.addProperty(VCARD.FN, person.getName())
					.addProperty(VCARD.Family, person.getFirstName())
					.addProperty(VCARD.Country, person.getCountry())
					.addProperty(VCARD.ADR, person.getAdresse())
					.addProperty(VCARD.TITLE, person.getProfession())
					.addProperty(VCARD.EMAIL, person.getEmail())
					.addProperty(VCARD.KEY, person.getPassword())
					.addProperty(VCARD.TEL, person.getTelephone())
					.addProperty(VCARD.Other, person.getSexe());

		// ==== Les liens de parenté
		classeMere.addSubClass(personClasse);
		
		//System.out.println("Ajout de personne terminé");

	}

 }