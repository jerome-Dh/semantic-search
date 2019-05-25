
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


	})

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
			};
			//user = $(this).serialize();
			console.log( user );
			saveUser(user);

		});
		
		//Connecter un user
		$("#form_connexion").submit(function(e){ 
			e.preventDefault(); 

			var email =  $("#email").val(),
				pasword =  $("#password").val();
			
			console.log( email + ' ' + pasword );
			logInUser(email, pasword);

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
	function saveUser(user)
	{
		
		var a = $.ajax({
			url: "/user",
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

			//Afficher la notif
			var texte = '' +
				'<div style="z-index:99999; position:fixed;top:80px; margin:auto; width:100%;"' +
				'class="text-center alert alert-success" role="alert">'+
			  'Votre compte a été créé avec succès!'+
			'</div>';
			$('body:first').append(texte);

			setTimeout(function(){ 
				location.reload();
			}, 5000);

		})
		.fail(function(result, textStatus, errorThrown) {
			console.log( "error" );
		})
		.always(function(data, textStatus, jqXHR) {
			console.log( "Statut" + textStatus );
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
				  console.log( "page not found" );
				}
			},

			timeout: 10000
		})
		.done(function(data, textStatus, jqXHR) {

			console.log( data );
			setTimeout(function(){ 
				// location.reload();
			}, 5000);			

		})
		.fail(function(result, textStatus, errorThrown) {
			console.log( "error" );
		})
		.always(function(data, textStatus, jqXHR) {
			console.log( "Statut" + textStatus );
		});
	}

	function logOutUser()
	{

	}

	
	
	