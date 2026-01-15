package com.blooging.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.blooging.blog.entites.Catagorey;



public interface CatagoreyRepo extends JpaRepository<Catagorey, Integer> {

	Catagorey findByCatagoryName(String catagoryNamename);
}