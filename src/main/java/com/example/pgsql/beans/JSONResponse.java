package com.example.pgsql.beans;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe répresentant le résultat à renvoyer sous forme JSON
 */
public class JSONResponse
{
	List<Disease> diseases = new ArrayList<Disease>();
	List<Medicament> medicaments = new ArrayList<Medicament>();
	List<Person> persons = new ArrayList<Person>();

	public JSONResponse()
	{

	}

	public void addDisease(Disease disease)
	{
		diseases.add(disease);
	}

	public void addDiseases(List<Disease> diseases)
	{
		this.diseases.addAll(diseases);
	}

	public void addPerson(Person person)
	{
		persons.add(person);
	}

	public void addMedicaments(Medicament medicament)
	{
		medicaments.add(medicament);
	}

	//===== Getters 
	public List<Disease> getDiseases()
	{
		return diseases;
	}
	public List<Medicament> getMedicaments()
	{
		
		return medicaments;
	}
	public List<Person> getPersons()
	{
		return persons;
	}
	
}
