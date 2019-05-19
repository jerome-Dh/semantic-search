package com.example.pgsql.beans;


/**
 * Classe réprésentant une maladie
 */
public class Disease
{

	private String label;
	private String comment;
	private String genre;
	private String lienWiki;

	public Disease(String label, String comment, String genre, String lienWiki)
	{
		setLabel(label);
		setComment(comment);
		setGenre(genre);
		setLienWiki(lienWiki);
	}

	//===== Getter
	public String getLabel()
	{
		return label;
	}

	public String getComment()
	{
		return comment;
	}

	public String getGenre()
	{
		return genre;
	}
	public String getLienWiki()
	{
		return lienWiki;
	}

	//==== Setter
	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	public void setLienWiki(String lienWiki)
	{
		this.lienWiki = lienWiki;
	}

}
