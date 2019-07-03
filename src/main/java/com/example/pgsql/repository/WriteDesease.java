
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

import com.example.pgsql.beans.Desease;

/**
 * Classe pour la gestion des écritures des deseases dans les ontologies
 *
 * @date 30/06/2019
 *
 * @author Jerome Dh
 */
 public class WriteDesease extends BaseWriter
 {

	/**
	 * Ontologie de deseases
	 *
	 * @var String 
	 */
    public static final String DESEASE_FILE = "O4TSSRDF.owl";

	/**
	 * Contructeur
	 *
	 * @param Desease desease
	 */
	public WriteDesease()
	{
	}
	
	//Exécuter l'action
	public void execute(Desease desease)
	{
		//charger le model
		OntModel model = this.getModel(DESEASE_FILE);

		// == Les namespaces         
		model.setNsPrefix(PREFIX, NS);

		//Ajouter des deseases
		this.ajouterDesease(model, desease);

		this.writeInFile(model, DESEASE_FILE);

	}
	
	
	/**
	 * Ajouter une desease
	 *
	 * @param OntModel model - Le model
	 * @param Desease desease - Le desease
	 */
	protected void ajouterDesease(OntModel model, Desease desease)
	{
		//Créer la classe mère "Person"
		OntClass deseaseMere = null;
	
		deseaseMere = model.getOntClass(CLASSE_DESEASE);
		if(deseaseMere == null)
		{
			deseaseMere = model.createClass(CLASSE_DESEASE);
		}
		
		this.addDesease(model, deseaseMere, desease);

	}
	
	/**
	 * Ajouter une desease
	 *
	 * @param OntModel model - Le model
	 * @param OntClass classeMere - La classe mère
	 * @param Desease desease - La desease
	 */
	protected void addDesease(OntModel model, OntClass classeMere, Desease desease)
	{
		int i = (int) (Math.random() * 10);
		String id = "Desease." + System.currentTimeMillis() + i;

		//Créer un objet Desease
		OntClass deseaseClasse = model.createClass(NS + id);

		//Ajout les propriétés et leurs valeurs
		deseaseClasse.addProperty(RDFS.label, model.createLiteral(desease.getLabel(), "fr"))
					 .addProperty(RDFS.comment, model.createLiteral(desease.getComment(), "fr"))
					 .addProperty(RDFS.member, model.createResource(NS + desease.getGenre()));

		// ==== Les liens de parenté
		classeMere.addSubClass(deseaseClasse);

	}

 }