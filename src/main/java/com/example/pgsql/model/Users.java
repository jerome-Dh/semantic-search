package com.example.pgsql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** 
 * Table Users 
 * @author Jerome Dh
 */
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@NotBlank
    @Size(min = 2, max = 100)
    private String name;
	
	@NotBlank
    @Size(min = 2, max = 100)
    private String firstName;
	
	@NotBlank
    @Size(min = 2, max = 100)
    private String email;

	@NotBlank
    @Size(min = 2, max = 100)
    private String password;

    @Size(min = 2, max = 100)
    private String profession;
	
	@NotBlank
    @Size(min = 1, max = 1)
    private String sexe;

	protected Users()
	{
		
	}

	public Users(String name, String firstName, String email,
		String password, String profession, String sexe)
	{
		setName(name);
		setFirstName(firstName);
		setEmail(email);
		setPassword(password);
		setProfession(profession);
		setSexe(sexe);
	}

    // Getters
	public Long getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getEmail()
	{
		return email;
	}
	public String getPassword()
	{
		return password;
	}
	public String getSexe()
	{
		return sexe;
	}
	public String getProfession()
	{
		return profession;
	}

	/** Setters **/
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setSexe(String sexe)
	{
		this.sexe = sexe;
	}
	
	public void setProfession(String profession)
	{
		this.profession = profession;
	}
	
	public String toString()
	{
		return name + " ; " + firstName + " ; " + email;
	}

}