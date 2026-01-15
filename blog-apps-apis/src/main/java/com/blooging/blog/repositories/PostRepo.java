package com.blooging.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.entites.User;
import com.blooging.blog.entites.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user, Pageable page);

	Page<Post> findByCatagorey(Catagorey catagory, Pageable page);
	
	List<Post> findByTitleContaining(String title);
	
}
