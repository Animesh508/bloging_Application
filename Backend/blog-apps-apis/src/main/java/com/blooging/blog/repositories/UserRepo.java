package com.blooging.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blooging.blog.entites.User;
import com.blooging.blog.payloads.UserDto;

public interface UserRepo extends JpaRepository<User, Integer> {
//JpaRepository gives you in built functionality/features in side UserRepo like sorting, pageing. 
	Optional<User> findByEmail(String email);
}
