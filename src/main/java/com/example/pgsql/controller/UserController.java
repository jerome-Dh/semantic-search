package com.example.pgsql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;

import com.example.pgsql.exception.ResourceNotFoundException;
import com.example.pgsql.model.Users;
import com.example.pgsql.repository.UserRepository;
import com.example.pgsql.beans.Login;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public Login getUser(
		HttpSession session,
		@RequestParam(required=true) String email,
		@RequestParam(required=true) String password
		)
	{
		boolean status = false;
		String comment = "";
		Users user = userRepository.findByEmail(email);
		if(user != null)
		{
			if(user.getPassword().equals(password))
			{
				status = true;
			}
			else
			{
				comment = "Mot de passe invalide !";
			}
		}
		else
		{
			comment = "Données introuvables, veuillez réessayer !";
		}

		session.setAttribute("user", user);

        return new Login(status, user, comment);

    }

    @PostMapping("/user")
    public Login createUser(@RequestBody Users user, HttpSession session) 
	{
		user = userRepository.save(user);
		boolean status = true;
		String comment = "";

		session.setAttribute("user", user);

		System.out.println(user);

		return new Login(status, user, comment);

	}
	
}