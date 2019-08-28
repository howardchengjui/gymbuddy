package com.codingdojo.gymbuddy.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.gymbuddy.models.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	
	Users findByEmail(String email);
}
