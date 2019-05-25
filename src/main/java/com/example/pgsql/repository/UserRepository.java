package com.example.pgsql.repository;

import com.example.pgsql.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	public Users findByEmail(String email);

}