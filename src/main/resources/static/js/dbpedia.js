

/***********************************************************
 *
 * 			SCRIPT DE TRAITEMENT AJAX
 *			@By Groupe 22
 *
 **********************************************************/

	/**
	* Variable d'état de recherche en Local /DBPedia
	*/
	var rechercheLocal = true;

	/**
	 * Lancer la recherche/configurer au début
	 */
	$(document).ready(function(){

		//Rechercher le queryString et lancer la réquête
		queryString();

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
				
				//console.log(rechercheLocal);
				
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

				if(rechercheLocal)
				{
					$( "#search" ).val( ui.item.label );
					localFullSearch(ui.item.label);
				}
				else
				{
					$( "#search" ).val( ui.item.titre.value );
					fullSearch(ui.item.titre.value);
				}

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
	 * Trouver le QueryString et lancer la recherche
	 */
	function queryString()
	{

		const urlParams = new URLSearchParams(window.location.search);
		const q = urlParams.get('q');
		const cible = urlParams.get('cible');

		//S'il y a le queryString
		if(q != null)
		{	
			console.log(q + ' et ' + cible);
			$( "#search" ).val(q);
			switcher(cible);
		}
		else
		{
			//on vérifie le storage local
			const cible_storage = localStorage.getItem('cible');

			if(cible_storage != null)
			{	
				console.log(' Cible stocké ' + cible_storage);
				switcher(cible_storage);
			}

		}
	}

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

		$('#choixCible > button:first').html(nom);
		$( "#search" ).focus();

		//Garder la préférence utilisateur
		localStorage.setItem('cible', nom);

		//Relancer la recherche en fonction du choix
		lancerRecherche();

		return false;

	}
	 
	/**
	 * Nettoyer les anciens résultats
	 */
	function clearOldResults()
	{
		$("#result").html('');
		$("#pancard-droite").html('');
		$('#modalZone').html('');
	}

	/**
	 * Lancer la recherche avec le terme en cours
	 * En local ou sur DBPedia
	 */
	function lancerRecherche()
	{
		var term = $( "#search" ).val();
		if(term.length > 1)
		{
			if(rechercheLocal)
			{
				localFullSearch(term);
			}
			else
			{
				fullSearch(term);
			}
		}
		else
		{
			$( "#search" ).focus();
		}

	}

	/** 
	 * Lancer la full recherche sur BDPedia 
	 */
	function fullSearch(term)
	{
		var new_term = getTermSearch(term);
		
		console.log("New term: " + new_term);
		
		var q = encodeURIComponent(
				'select distinct ?titre ?resume ?description ?wiki ?image ' +
				'where '+
				'{ ' +
					'{ ' +
						'?drug a <http://dbpedia.org/ontology/Drug> ;' +
							'rdfs:label ?titre ;' +
							'dbo:abstract ?resume ;' +
							'rdfs:comment ?description .' +
						'Optional {' +
							'?drug foaf:isPrimaryTopicOf ?wiki ' +
						'} ' +
						'optional{ ' +
							'?drug dbo:thumbnail ?image ' +
						'} ' +
					'} ' +
					'UNION ' +
					'{ ' +
						'?maladie a <http://dbpedia.org/ontology/Disease> ;' +
							'rdfs:label ?titre ;' +
							'dbo:abstract ?resume ;' +
							'rdfs:comment ?description .' +
						'Optional {' +
							'?maladie foaf:isPrimaryTopicOf ?wiki ' +
						'} ' +
						'optional{ ' +
							'?maladie dbo:thumbnail ?image ' +
						'} ' +
					'} ' +
					'FILTER langMatches(lang(?resume), "fr") ' +
					'FILTER langMatches(lang(?description), "fr") ' +
					'FILTER langMatches(lang(?titre), "fr") ' +
					'FILTER REGEX(?titre, "'+ new_term + '") '+
				'} LIMIT 10'
			);

		var url = 'http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query='+ q +'&output=json';

		$.getJSON(
			url+"&callback=?",
			function (data) {
				console.log(data);
				printSearch(data.results.bindings);

			}
		);

	}
	
	/** Lancer la full recherche en local **/
	function localFullSearch(term)
	{
		
		console.log("New term: " + term);
				
		var url = 'http://localhost:8080/fullsearch?term=' + term
		$.getJSON(
			url,
			function (data) {
				console.log(data);
				printLocalSearch(data.diseases);

			}
		);

	}

	/** 
	 * Retourner la chaine de recherche pour filtrer 
	 *
	 */
	function getTermSearch(term)
	{
		if(typeof term !== 'undefined')
		{
			var chaines = term.split(' '),
				taille = chaines.length,
				response = [];
			for(var i=0; i < taille; ++i)
			{
				if(chaines[i].length > 2)
					response.push(chaines[i]);
			}

			return '(' + term + ')|(' + response.join(')|(') + ')';
		}

		return texte;

	}

	/**
	 * 
	 * Imprimer le résultat d'une recherche
	 * 
	 * @param data - tableau de données 
	 */
	function printSearch(data)
	{

		if(Array.isArray(data))
		{
			var taille = data.length;
			var template;
			clearOldResults();

			var pancard_deja = false;

			for(var i=0; i < taille; ++i)
			{
				var photo = (typeof data[i].image === 'undefined') ? false : data[i].image.value,
					wiki = (typeof data[i].wiki === 'undefined') ? false : data[i].wiki.value;

				if( ! pancard_deja)
				{
					pancard_deja = true;
					show_pancard(
						data[i].titre.value, 
						data[i].resume.value,
						data[i].description.value,
						wiki,
						photo
					);
				}

				$("#result").append(
					getForTemplate(i,
						data[i].titre.value, 
						data[i].resume.value,
						wiki,
						false
					)
				);

				makeModalDialod(i,
					data[i].titre.value, 
					data[i].resume.value,
					data[i].description.value,
					wiki,
					photo
				);
			}

		}

	}

	
	 
	/**
	 * 
	 * Imprimer le résultat d'une recherche locale
	 * 
	 * @param data - tableau de données 
	 */
	function printLocalSearch(data)
	{

		if(Array.isArray(data))
		{
			var taille = data.length;
			var template;
			clearOldResults();

			var pancard_deja = false;

			for(var i=0; i < taille; ++i)
			{
				var label = data[i].label,
					comment = (typeof data[i].comment === 'undefined') ? false : data[i].comment,
					genre = (typeof data[i].genre === 'undefined') ? false : data[i].genre,
					lienWiki = (typeof data[i].lienWiki === 'undefined') ? false : data[i].lienWiki,
					photo = "";

				if(lienWiki != false && ~lienWiki.toLowerCase().indexOf('url:'))
					lienWiki = lienWiki.substr(4);

				if( ! pancard_deja)
				{
					pancard_deja = true;
					show_pancard(
						label, 
						comment,
						genre,
						lienWiki,
						photo
					);
				}

				$("#result").append(
					getForTemplate(i,
						label, 
						comment,
						lienWiki
					)
				);

				makeModalDialod(i,
					label, 
					genre,
					comment,
					lienWiki,
					photo
				);
			}

		}

	}

	/**
	 * Construire un template
	 *
	 */
	function getForTemplate(id, titre, resume, wiki, local = true)
	{
		var makeTemplate,
			lienWiki = local ? wiki: 'Voir sur Wikipedia [EN]';

		  makeTemplate = '<div class="row" id="row-'+ id + '">'+
			'<div class="col-md-12">' +
			  '<div class="card mb-4 shadow-sm">'+
				'<h4 style="margin: 20px 20px 0; font-size: 18px;"><a >' + titre.charAt(0).toUpperCase() + titre.substr(1) + '</a> </h4>'+
				'<div class="card-body" style="margin-top: 10px;">'+
				  '<p class="card-text">...'+ resume.substr(0, 200) + '...</p>'+
				  '<div class="d-flex justify-content-between align-items-center">'+
					'<div class="btn-group">'+
					  '<button type="button" class="btn btn-sm btn-outline-secondary"' +
					  'data-toggle="modal" data-target="#modal-' + id + '">'+
						'Voir</button> '+
						' &nbsp; '+
						'<small><a href="' + wiki + '" target="_blank">' + lienWiki + '</a></small>'+
					'</div>'+
					'<small class="text-muted">date</small>'+
				  '</div>'+
				'</div>'+
			  '</div>'+
			'</div>'+
		'</div>';

		  return makeTemplate;

	}
	
	function show_pancard(titre, resume, description, wiki, photo)
	{
		var photo_html = (photo == false) ? 
		'<svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Image"><title>Image</title><rect width="100%" height="100%" fill="#55595c"/><text x="50%" y="50%" fill="#eceeef" dy=".3em">Image manquante !</text></svg>'
            :
			'<div class="col-md-12 ml-auto">'+
						'<img src="' + photo +'" width="100%" alt="illustration"/>'+
					'</div>';

		var wiki_html = (wiki == false) ? '' : 
					'<div class="col-md-10">'+
						'<a href="' + wiki + '" target="_blank">Sur le Web</a>'+
					'</div>';

		var template = '' +
			'<div class="card mb-4 shadow-sm">'+
            photo_html+
			'<div class="card-body">'+
              '<p class="card-text">' +
			  '<strong style="margin: 20px 0 0; font-size: 18px;">' + titre.charAt(0).toUpperCase() + titre.substr(1)  + '</strong><br/>'+
			  resume.substr(0, 200) +  
			  '..</p>' +
              '<div class="d-flex justify-content-between align-items-center">'+
                '<div class="btn-group">'+
				  '<button type="button" class="btn btn-sm btn-outline-secondary">'+
				  '<a href="' + wiki + '" target="_blank"> Voir </a>'+
				  '</button>'+
				  ' &nbsp; '+
                  wiki_html +
				  '</div>'+
                '<small class="text-muted">date</small>'+
              '</div>'+
            '</div>'+
          '</div>';			
		 console.log(template);
		
		$('#pancard-droite').html('').append(template);

	}

	/** 
	 * Construire le modal dialog
	 */
	function makeModalDialod(id, titre, resume, description, wiki, photo)
	{
		console.log(photo +  " Wiki: " + wiki);
		var photo_html = (photo == false) ? '' : '<div class="col-md-12 ml-auto">'+
						'<img src="' + photo +'" width="100%" alt="illustration"/>'+
					'</div>';

		var wiki_html = (wiki == false) ? '' : 
					'<div class="col-md-12">'+
						'<a href="' + wiki + '" target="_blank">' + wiki + '</a>'+
					'</div>';

		var template = '' +
		'<div class="modal fade" id="modal-'+ id +'" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">'+
		  '<div class="modal-dialog modal-dialog-centered modal-lg" role="document">'+
			'<div class="modal-content">'+
			  '<div class="modal-header">'+
				'<h5 class="modal-title" id="exampleModalLongTitle">' + titre.charAt(0).toUpperCase() + titre.substr(1) + '</h5>'+
				'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
				  '<span aria-hidden="true">&times;</span>'+
				'</button>'+
			  '</div>'+
			  '<div class="modal-body">'+
				'<div class="container-fluid">'+
					'<div class="row">'+
						photo_html +
					'</div>'+
					'<div class="row" style="margin: 20px 0 0;">'+
						'<div class="col-md-12 ml-auto">'+
							'<p>' + description + '</p>'+
						'</div>'+
					'</div>'+
					'<div class="row">'+
						wiki_html +
					'</div>'+

				'</div>'+
			  '</div>'+
			  '<div class="modal-footer">'+
				'<button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>'+
				'<button type="button" class="btn btn-primary" data-dismiss="modal">Continuer</button>'+
			  '</div>'+
			'</div>'+
		  '</div>'+
		'</div>';

	   $('#modalZone').append(template);

	}
	

