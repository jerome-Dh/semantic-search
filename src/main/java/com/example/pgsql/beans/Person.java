package com.example.pgsql.beans;

/**
 * Classe réprésentant une personne
 */
public class Person
{
	private String name;
	private String firstName;
	private String comment;

	public Person(String name, String firstName, String comment)
	{
		setName(name);
		setFirstName(firstName);
		setComment(comment);
	}

	//===== Getter
	public String getName()
	{
		return name;
	}

	public String getComment()
	{
		return comment;
	}

	public String getFirstName()
	{
		return firstName;
	}

	//==== Setter
	public void setName(String name)
	{
		this.name = name;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
}