
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

		//Vérifier la valeur de paramétrage des threads
		const thread_storage = localStorage.getItem('thread');

		if(thread_storage != null)
		{
			console.log(' thread_storage stocké ' + thread_storage);
			var t = (thread_storage == '1') ? true : false;
			$('#checkbox_processeur').prop('checked', t);
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
	 * Trouver la valeur de la langue utilisateur
	 */
	function getUserLanguage()
	{
		var lang = $('#choiceLang').val();
		return lang;
	}