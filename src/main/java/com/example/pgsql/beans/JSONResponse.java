package com.example.pgsql.beans;

import java.util.List;
import java.util.ArrayList;

import com.example.pgsql.model.Users;

/**
 * Classe répresentant le résultat à renvoyer sous forme JSON
 */
public class JSONResponse
{
	List<Desease> diseases = new ArrayList<Desease>();
	List<Medicament> medicaments = new ArrayList<Medicament>();
	List<Users> user = new ArrayList<Users>();

	public JSONResponse()
	{

	}

	public void addDisease(Desease Desease)
	{
		diseases.add(Desease);
	}

	public void addDiseases(List<Desease> diseases)
	{
		this.diseases.addAll(diseases);
	}

	public void addUsers(Users user)
	{
		this.user.add(user);
	}

	public void addMedicaments(Medicament medicament)
	{
		medicaments.add(medicament);
	}

	//===== Getters 
	public List<Desease> getDiseases()
	{
		return diseases;
	}
	public List<Medicament> getMedicaments()
	{
		
		return medicaments;
	}
	public List<Users> getUsers()
	{
		return user;
	}
	
}
