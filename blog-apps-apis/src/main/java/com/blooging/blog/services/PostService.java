package com.blooging.blog.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blooging.blog.entites.Post;
import com.blooging.blog.payloads.PostDto;
import com.blooging.blog.payloads.PostResponce;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer catagoreyId);
	
	//create for storing multiple data at same time
	List<PostDto> createMultiplePost(List<PostDto> postDtos, Integer userId, Integer catagoreyId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all post
	PostResponce getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

	// get single post
	PostDto getByPostId(Integer postId);

	// get all posts by catogry
	PostResponce getPostByCatagory(Integer Id , Integer pageNumber, Integer pageSize);

	// get all post by user
	PostResponce getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

	// search posts
	List<PostDto> searchPost(String Keyword);
	
	//In file service Interface
	
	String uploadImage(String path, MultipartFile file) throws IOException;
	
	InputStream getResource(String path, String fileName) throws IOException;
	
	String getContentType(String fileName);

}
