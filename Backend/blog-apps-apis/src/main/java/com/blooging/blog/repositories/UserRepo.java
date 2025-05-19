package com.blooging.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blooging.blog.entites.User;

public interface UserRepo extends JpaRepository<User, Integer> {
//JpaRepository gives you in built functionality/features in side UserRepo like sorting, pageing. 
}
