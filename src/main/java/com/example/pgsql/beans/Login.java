package com.example.pgsql.beans;

import com.example.pgsql.model.Users;

/**
 * Classe réprésentant un login utilisateur
 */
public class Login
{

	private boolean status;
	private Users user;
	private String comment;

	public Login(boolean status, Users user, String comment)
	{
		setStatus(status);
		setUser(user);
		setComment(comment);
	}
	public Login()
	{
		
	}

	//===== Getter
	public boolean getStatus()
	{
		return status;
	}

	public Users getUser()
	{
		return user;
	}
	public String getComment()
	{
		return comment;
	}

	//==== Setter
	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public void setUser(Users user)
	{
		this.user = user;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

}
