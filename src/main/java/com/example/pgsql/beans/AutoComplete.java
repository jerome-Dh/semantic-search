package com.example.pgsql.beans;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe pour gérer l'autocompletion
 *
 */
public class AutoComplete
{
	private String label;
	private String description;

	public AutoComplete(String label, String description)
	{
		setLabel(label);
		setDescription(description);
	}

	public String getLabel()
	{
		return label;
	}

	public String getDescription()
	{
		return description;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

}
