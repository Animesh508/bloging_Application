package com.blooging.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blooging.blog.entites.Catagorey;

public interface CatagoreyRepo extends JpaRepository<Catagorey, Integer> {
	
	Catagorey findByCatagoryName(String catagoryNamename);

}
