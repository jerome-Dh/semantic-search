
	//////////////////////////////////////////////////////////
	// 
	//		Gestion des utilisateurs
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

		//Enregistrer un user
		$("#form_inscription").submit(function(e){ 

			e.preventDefault();

			var user = {
				'name': $("#name").val(),
				'firstName':  $("#firstName").val(),
				'email':  $("#your_email").val(),
				'password':  $("#your_password").val(),
				'profession':  $("#profession").val(),
				'sexe':  (($('#sexe_0').is(':checked')) ? 'h' : 'f')
			},
			url = $(this).attr('action');
			
			console.log('Target: ' + url);

			//Notification
			scrollTo(0, 0);
			traitementEncours('#form_inscription');

			//user = $(this).serialize();
			console.log( user );
			saveUser( user, url );

		});
		
		//Connecter un user
		$("#form_connexion").submit(function(e){ 

			e.preventDefault();

			//Notification
			scrollTo(0, 0);
			traitementEncours('#connexion-body');

			var email =  $("#email").val(),
				pasword =  $("#password").val();
			
			console.log( email + ' ' + pasword );
			logInUser(email, pasword);

		});
		
		/**
		 * Suppression de compte 
		 */
		$('#form_delete_account').submit(function(){

			var texte = 'Voulez-vous vraiment supprimer votre compte ?\n'+
				'Cette action est irreversible.\nCliquez sur oui pour confirmer la suppression !';

			return window.confirm(texte);

		});
		 
		
		
		console.log("Le script users.js passe ! ");

	});
	
	/////////////////////////////////////////////////////
	//
	//		Utilitaires
	//
	////////////////////////////////////////////////////

	 /**
	 * Save the user
	 */
	function saveUser(user, myUrl)
	{
		
		var a = $.ajax({
			url: myUrl,
			method: 'POST',
			data: JSON.stringify(user),
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			statusCode: {
				404: function() {
				  console.log( "page not found" );
				}
			},

			timeout: 10000

		})
		.done(function(data, textStatus, jqXHR) {

			console.log(data);

			afficherNotif('Votre compte a été créé avec succès !', 'success', '#form_inscription', 'check');

			setTimeout(function(){
				location.href='/account';
			}, 3000);

		})
		.fail(function(result, textStatus, errorThrown) {
			console.log( "Echec requete" );
			afficherNotif('Echec de la réquête !', 'warning', '#form_inscription', 'question');
		})
		.always(function(data, textStatus, jqXHR) {
			console.log( "Statut: " + textStatus );
		});

	}

	function logInUser(email, pasword)
	{
		var a = $.ajax({
			url: "/login",
			method: 'GET',
			data: ('email=' + email + '&password=' + pasword),
			dataType : 'json',
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
			

			if(typeof data === 'object')
			{

				if(typeof data.status !== 'undefined' && data.status == false )
				{
					//Afficher la notif d'echec
					afficherNotif(data.comment, 'warning', '#connexion-body', 'question');
				}
				else
				{
					afficherNotif(data.comment, 'success', '#connexion-body', 'check');

					setTimeout(function(){
						location.reload();
					}, 3000);
				}
			}

		})
		.fail(function(result, textStatus, errorThrown) {
			console.log( "Echec requete" );
			afficherNotif('Echec de la réquête !', 'warning', '#connexion-body', 'question');
		})
		.always(function(data, textStatus, jqXHR) {
			console.log( "Statut: " + textStatus );
		});
	}

	function logOutUser()
	{

	}

	/**
	 * Afficher une notification
	 */
	function afficherNotif(texte, type, id, fa_icon)
	{
		type = (type === 'undefined') ? 'success' : type;
		$('.notification').remove();

		//Afficher la notif
		var texte = '' +
			'<div style="margin:10px auto; width:100%;"'
			+ 'class="notification text-center alert alert-' + type + ' alert-dismissible fade show" role="alert">'
			+ '<strong><i class="fa fa-' + fa_icon + '"></i>' + texte + '</strong>'
			+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
			+	'<span aria-hidden="true">&times;</span>'
			+	'</button>' 
		+ '</div>';

		$(id).prepend(texte);
	}

	/**
	 * Afficher la notification de traitement En cours
	 */
	function traitementEncours(id)
	{
		afficherNotif('Traitement en cours ! veuillez patienter ..', 'info', id, 'circle-o-notch fa-spin');
	}
	
	
	