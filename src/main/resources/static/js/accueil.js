
/*************************************************************
 * 
 *			SCript pour la page d'accueil
 *
 ************************************************************/
 
	/**
	* Variable d'état de recherche en Local /DBPedia
	*/
	var rechercheLocal = true;

	/**
	 * Lancer la recherche/configurer au début
	 */
	$(document).ready(function(){


		//on vérifie le storage local pour les préférences utilisateurs
		const cible_storage = localStorage.getItem('cible');

		if(cible_storage != null)
		{	
			console.log(' Cible stocké ' + cible_storage);
			switcher(cible_storage);
		}

	})

	/**
	 * Enregistrer les évenements
	 */
	$( function() {

		$( "#search" ).autocomplete({
		  minLength: 2,
		  delay: 5,
		  source: function (request, response) {

				console.log(request.term);
				
				var mot_cle = request.term;
				
				//alert(rechercheLocal);

				//Si la recherche se fait en local
				if(rechercheLocal)
				{
					var url = 'http://localhost:8080/autocomplete?term=' + mot_cle;
					$.getJSON(
						url,
						function (data) {
							console.log(data);
							response(data);
						}
					);
					
				}
				else
				{
					//Lancer la recherche sur DBPedia
					
					var q = encodeURIComponent(
						'select distinct ?titre '+
						'where '+
						'{ '+
							'{ ?drug a <http://dbpedia.org/ontology/Drug>; '+
								'rdfs:label ?titre. } '+
							'UNION '+
							'{ ?maladie a <http://dbpedia.org/ontology/Disease>; '+
								'rdfs:label ?titre. } '+
							'FILTER langMatches(lang(?titre), "fr") '+
							'FILTER REGEX(?titre, "'+ request.term + '") '+
						'} LIMIT 15 '
						);

					var url = 'http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query='+ q +'&output=json';
					$.getJSON(
						url+"&callback=?",
						function (data) {
							console.log(data);
							response(data.results.bindings);
						}
					);
				}
			},

			focus: function( event, ui ) {
				//Le curseur est sur l'élement
				//$( "#search" ).val( ui.item.titre.value );
				return false;
			},
			select: function( event, ui ) {

				//Un élement est sélectionné dans la liste

				var term;
				if(rechercheLocal)
				{
					term = ui.item.label;
				}
				else
				{
					term =  ui.item.titre.value;
				}
				
				$( "#search" ).val( term );
						
				lancerRecherche();

				return false;

			}
		})
		.autocomplete( "instance" )._renderItem = function( ul, item ) {

			//Processus de rendu

			var texte = '';
			if(rechercheLocal)
			{
				texte = item.label;		
			}
			else
			{
				texte = item.titre.value;
			}

			return $( "<li>" )
				.append( "<div>" + texte + "</div>" )
				.appendTo( ul );

		};
		
		/** 
		 * Lancer la recherche avec le bouton Rechercher
		 */
		$('#btn-search').click(lancerRecherche);

		console.log("Ca passe ! ");

	});

	 /**
	 * Lancer la recherche avec le terme en cours
	 * En local ou sur DBPedia
	 */
	function lancerRecherche()
	{
		$('#zone-recherche form:first').submit();
	}

	$("#zone-recherche form:first").submit(function(e){

		//e.preventDefault(); // Le navigateur ne peut pas envoyer le formulaire

		//Envoyer le formulaire
		if($( "#search" ).val().length > 0)
		{
			//Mettre à jour le champ de cible
			$(' input[name="cible"] ').val($('#choixCible > button:first').html());

			return true;
		}
		else
		{
			$( "#search" ).focus();
			return false;
		}
		
	});
	
	/*
	 * Gérer le switch entre la recherche locale et DBPedia
	 */
	function switcher(type)
	{
		var nom = "";
		if(type.toLowerCase() == 'epicam')
		{
			rechercheLocal = true;
			nom = 'Epicam';
		}
		else
		{
			nom = 'DBPedia';
			rechercheLocal = false;
		}

		//Garder la préférence utilisateur
		localStorage.setItem('cible', nom);

		$('#choixCible > button:first').html(nom);
		$( "#search" ).focus();

		return false;

	}