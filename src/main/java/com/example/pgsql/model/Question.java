package com.example.pgsql.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** 
 * Table Question 
 * @author 
 */
@Entity
@Table(name = "questions")
public class Question extends AuditModel {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    // Getters and Setters (Omitted for brevity)
	public Long getId()
	{
		return id;
	}
	public String getTitle()
	{
		return title;
	}
	public String getDescription()
	{
		return description;
	}
	
	/** Setters **/
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
}