
	/////////////////////////////////////////////////////
	//
	//		Script principal
	//
	////////////////////////////////////////////////////


	$(document).ready(function(){
		
		//Initialisation
		parametrage_initiale();

	})


	$( function() {
		
		/**
		 * Switch en simple ou plusieurs processeurs
		 */
		$('#checkbox_processeur').click(getParamThread);

		/**
		 * Utilisation du reasoner
		 */
		$('#checkbox_reasoner').click(config_reasoner);

	});


	/////////////////////////////////////////////////////
	//
	//		Utilitaires
	//
	////////////////////////////////////////////////////


	/**
	 * Initialisation des élements de la page
	 */
	function parametrage_initiale()
	{
		console.log(' Paramétrage initiale ');

		//Activation/désactivation des threads
		const thread_storage = localStorage.getItem('thread');

		if(thread_storage != null)
		{
			console.log(' thread_storage stocké ' + thread_storage);
			var t = (thread_storage == '1') ? true : false;
			$('#checkbox_processeur').prop('checked', t);
		}

		//Reasoner
		const reasoner_storage = localStorage.getItem('reasoner');
		if(reasoner_storage != null)
		{
			var t = (reasoner_storage == 'active') ? true : false;
			$('#checkbox_reasoner').prop('checked', t);
			
			//Sauver sur serveur
			config_reasoner();
		}
	
	}
  
	/**
	 * Trouver la valeur de paramétrage des threads
	 */
	function getParamThread()
	{
		var ret = $('#checkbox_processeur').is(':checked') ? '1' : '0';

		//Garder la préférence utilisateur
		localStorage.setItem('thread', ret);

		return ret;

	}
	
	/**
	 * Configurer l'utilisation du reasoner
	 */
	function config_reasoner()
	{
		var etat = $('#checkbox_reasoner').is(':checked') ? 'active' : 'desactive';

		//Garder la préférence sur le raisonner
		localStorage.setItem('reasoner', etat);

		var a = $.ajax({
			url: "/reasoner",
			method: 'GET',
			data: ('term=' + etat),
			dataType : 'text',
			contentType : 'text/plain; charset=utf-8',
			statusCode: {
				404: function() {
				  console.log( "Page not found" );
				}
			},

			timeout: 10000

		})
		.done(function(data, textStatus, jqXHR) {

			console.log( data );


		})
		.fail(function(result, textStatus, errorThrown) {
			console.log( "Echec requete" );
		})
		.always(function(data, textStatus, jqXHR) {
			console.log( "Statut: " + textStatus );
		});

	}
	
	/**
	 * Trouver la valeur de la langue utilisateur
	 */
	function getUserLanguage()
	{
		var lang = $('#choiceLang').val();
		return lang;
	}