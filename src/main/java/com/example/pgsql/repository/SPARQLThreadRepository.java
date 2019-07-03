package com.example.pgsql.repository;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.example.pgsql.beans.*;


/**
 * Repository pour la gestion des réquêtes SPARQL en Parallèle
 *
 */
public class SPARQLThreadRepository
{
	//La session en cours
	private HttpSession session;
	
	public SPARQLThreadRepository(HttpSession session)
	{
		this.session = session;
	}

	/**
	 * Gestion de l'autocomplétion
	 */
	public List<AutoComplete> findForAutoComplete(String term)
	{
		System.out.println("+----------- Debut Execution -------------+");
		List<AutoComplete> list = new ArrayList<AutoComplete>();
		List<AutoComplete> list1 = new ArrayList<AutoComplete>();

		/* === OMP CONTEXT === */
		class OMPContext {
			public String param_term;
			public List<AutoComplete> local_list;
			public List<AutoComplete> local_list1;
		}

		final OMPContext ompContext = new OMPContext();
		ompContext.param_term = term;
		ompContext.local_list = list;
		ompContext.local_list1 = list1;

		final org.omp4j.runtime.IOMPExecutor ompExecutor = new org.omp4j.runtime.DynamicExecutor(Runtime.getRuntime().availableProcessors());

		/* === /OMP CONTEXT === */
		for (int ompI = 0; ompI < Runtime.getRuntime().availableProcessors(); ompI++) 
		{
			final int ompJ = ompI;
			ompExecutor.execute(new Runnable(){
				@Override
				public void run() 
				{{

					if (ompJ == 0) {{

						// Réquêtes vers O4TSS
						O4TSSRDF basis1 = new O4TSSRDF(session);
						ompContext.local_list.addAll(basis1.autoCompleteQuery(ompContext.param_term));

					}}
					else if (ompJ == 1) 
					{{
						// Réquêtes vers O4UP
						O4UPRDF basis2 = new O4UPRDF(session);
						ompContext.local_list1.addAll(basis2.autoCompleteQuery(ompContext.param_term));

					}}

				}}

			});

		}

		//Attendre la fin des exécutions
		ompExecutor.waitForExecution();

		System.out.println("+----------- Exécution terminée -------------+");

		list.addAll(list1);

		return list;

	}

	/**
	 * Gestion des réquêtes à réponses complètes
	 */
	public JSONResponse fullSearch(String termFull)
	{
		// Les listes
		List<Desease> list2 = new ArrayList<Desease>();
		List<Desease> list3 = new ArrayList<Desease>();

		/* === OMP CONTEXT === */
		class OMPContext {
			public String param_termFull;
			public List<Desease> local_list2;
			public List<Desease> local_list3;
		}

		final OMPContext ompContext = new OMPContext();
		ompContext.param_termFull = termFull;
		ompContext.local_list2 = list2;
		ompContext.local_list3 = list3;

		final org.omp4j.runtime.IOMPExecutor ompExecutor = new org.omp4j.runtime.DynamicExecutor(Runtime.getRuntime().availableProcessors());

		/* === /OMP CONTEXT === */
		for (int ompI = 0; ompI < Runtime.getRuntime().availableProcessors(); ompI++) 
		{
			final int ompJ = ompI;
			ompExecutor.execute(new Runnable(){
				@Override
				public void run() 
				{{

					if (ompJ == 0) {{

						// Réquêtes vers O4TSS
						O4TSSRDF basis1 = new O4TSSRDF(session);
						ompContext.local_list2.addAll(basis1.fullQuery(ompContext.param_termFull));

					}}
					else if (ompJ == 1) 
					{{
						// Réquêtes vers O4UP
						O4UPRDF basis2 = new O4UPRDF(session);
						ompContext.local_list3.addAll(basis2.fullQuery(ompContext.param_termFull));

					}}

				}}

			});

		}

		//Attendre la fin des exécutions
		ompExecutor.waitForExecution();

		// Concevoir la réponse
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.addDiseases(list2);
		jsonResponse.addDiseases(list3);

		return jsonResponse;

	}

}