package com.blooging.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blooging.blog.config.AppConstants;
import com.blooging.blog.entites.Post;
import com.blooging.blog.entites.User;
import com.blooging.blog.payloads.ApiResponce;
import com.blooging.blog.payloads.PostDto;
import com.blooging.blog.payloads.PostResponce;
import com.blooging.blog.payloads.UserDto;
import com.blooging.blog.services.CatagoryService;
import com.blooging.blog.services.FileService;
import com.blooging.blog.services.PostService;
import com.blooging.blog.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private CatagoryService catagoryService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	

	// create
	@PostMapping("/user/{userId}/category/{catagoreyId}/post")
	public ResponseEntity<PostDto> createpost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer catagoreyId) {
		PostDto createPost = this.postService.createPost(postDto, userId, catagoreyId);

		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// create batch post creation
	@PostMapping("user/{userId}/category/{catagoreyId}/posts")
	public ResponseEntity<List<PostDto>> createPost(@RequestBody List<PostDto> postDtos, @PathVariable Integer userId,
			@PathVariable Integer catagoreyId) {
		List<PostDto> createPost = this.postService.createMultiplePost(postDtos, userId, catagoreyId);
		return new ResponseEntity<List<PostDto>>(createPost, HttpStatus.CREATED);
	}

	// Get all post on the basis of user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponce> getpostByUsers(@PathVariable Integer userId,
			@RequestParam(value = "pagenumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		PostResponce posts = this.postService.getPostByUser(userId, pageNumber, pageSize);
		return new ResponseEntity<PostResponce>(posts, HttpStatus.OK);
	}

	// Get all post on the basis of catagorey

	@GetMapping("/category/{Id}/catagoreypost")
	public ResponseEntity<PostResponce> getPostByCategory(@PathVariable Integer Id,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		PostResponce posts = this.postService.getPostByCatagory(Id, pageNumber, pageSize);
		return new ResponseEntity<PostResponce>(posts, HttpStatus.OK);
	}

	// get all post
	@GetMapping("/post/all")
	public ResponseEntity<PostResponce> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponce postResponce = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponce>(postResponce, HttpStatus.OK);
	}

	// get all post by Id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		PostDto post = this.postService.getByPostId(postId);

		return new ResponseEntity<PostDto>(post, HttpStatus.OK);

	}

	// Delete
	@DeleteMapping("/post/{postId}")
	public ApiResponce deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponce("post is succesfuly deleted", true);

	}

	@PutMapping("/post/{postId}")

	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// search
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> result = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

	// post img upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		
		//Validate file
		if(image.isEmpty()) {
			throw new RuntimeException("File is empty");
		}
		
		//validate file type
		String contentType = image.getContentType();
		if(contentType==null || !contentType.startsWith("image/")) {
			throw new RuntimeException("Invalid file type. Only images are allowed. ");
		}
		
		PostDto postDto = this.postService.getByPostId(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImgName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}
	
	// get image from the data base
	@GetMapping(value="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = this.postService.getResource(path, imageName);
		String contentType = this.postService.getContentType(imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	

}
