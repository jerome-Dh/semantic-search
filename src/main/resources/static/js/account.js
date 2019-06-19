

	//////////////////////////////////////////////////////////
	// 
	//		Gestion des comptes
	//
	//////////////////////////////////////////////////////////
 
	/**
	* Variable globale
	*/

	/**
	 * Document prêt
	 */
	$(document).ready(function(){

		

	});
	
	/**
	 * Enregistrer les évenements
	 */
	$( function() {

		//Désactiver les champs
		$('#account input, #account select').attr('disabled', true);

		//Modifier le nom du button et 
		$('#account [type="submit"]')
			.text("Modifier")
			.attr('type', 'button');
	
		//Enregistrer le click sur le button
		$('#account [type="button"]')
			.on('click', function(){
				$(this).attr('type', 'submit').text("Valider");
				$('#account input, #account select').attr('disabled', false);
			});

	});
	