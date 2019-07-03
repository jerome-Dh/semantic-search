package com.example.pgsql.repository;

import com.example.pgsql.model.Users;

/**
 * Gestion de l'ontologie des utilisateurs
 *
 * @date 23/06/2019
 * 
 * @author Jerome Dh
 */

public class UserRDFRepository 
{

	public UserRDFRepository()
	{
	}
	
	/**
	 * Ajouter un utilisateur 
	 *
	 * @param Users - Utilisateur
	 *
	 * @return boolean 
	 */
	public boolean create(Users user)
	{
		WritePerson wP = new WritePerson();
		wP.execute(user);

		return true;
	}

	/**
	 * Ajouter les préférences de l'utilisateur 
	 *
	 * @param Users - Utilisateur
	 * @param String prefs - Les préférences
	 *
	 * @return boolean 
	 */
	public boolean addPreferences(Users user, String prefs)
	{
		WritePreference wPref =  new WritePreference();
		wPref.execute(user, prefs);
		
		return true;
	}
	
	

}