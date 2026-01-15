package com.blooging.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.entites.Post;
import com.blooging.blog.entites.User;
import com.blooging.blog.exceptions.ResourceNotFoundException;
import com.blooging.blog.payloads.PostDto;
import com.blooging.blog.payloads.PostResponce;
import com.blooging.blog.repositories.CatagoreyRepo;
import com.blooging.blog.repositories.PostRepo;
import com.blooging.blog.repositories.UserRepo;
import com.blooging.blog.services.PostService;

@Service
public class PostServiceImp implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CatagoreyRepo catagoreyRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catagoreyId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", userId));
		Catagorey catagorey = this.catagoreyRepo.findById(catagoreyId)
				.orElseThrow(() -> new ResourceNotFoundException("Catagorey ", "catagorey id", catagoreyId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImgName("deafult.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCatagorey(catagorey);

		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setImgName(postDto.getImgName());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID ", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponce getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> post = pagePost.getContent();
		List<PostDto> postDto = post.stream().map((posts) -> this.modelMapper.map(posts, PostDto.class))
				.collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDto);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}

	@Override
	public PostDto getByPostId(Integer postId) {
		Post posts = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		return this.modelMapper.map(posts, PostDto.class);
	}

	@Override
	public PostResponce getPostByCatagory(Integer Id, Integer pageNumber, Integer pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);

		Catagorey cat = this.catagoreyRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CatagoreyId", Id));
		Page<Post> pagePost = this.postRepo.findByCatagorey(cat, page);
//		List<Post> post = this.postRepo.findByCatagorey(cat);
		List<PostDto> postDtos = pagePost.stream().map((posts) -> this.modelMapper.map(posts, PostDto.class))
				.collect(Collectors.toList());

		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}

	@Override
	public PostResponce getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Page<Post> postUser = this.postRepo.findByUser(user, page);
//		List<Post> post = this.postRepo.findByUser(user);
		List<PostDto> postDtos = postUser.stream().map((posts) -> this.modelMapper.map(posts, PostDto.class))
				.collect(Collectors.toList());

		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(postUser.getNumber());
		postResponce.setPageSize(postUser.getSize());
		postResponce.setTotalElements(postUser.getTotalElements());
		postResponce.setTotalPages(postUser.getTotalPages());
		postResponce.setLastPage(postUser.isLast());
		return postResponce;
	}

	@Override
	public List<PostDto> searchPost(String Keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(Keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	// Batch Post Creation

	@Override
	public List<PostDto> createMultiplePost(List<PostDto> postDtos, Integer userId, Integer catagoreyId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", userId));
		Catagorey catagorey = this.catagoreyRepo.findById(catagoreyId)
				.orElseThrow(() -> new ResourceNotFoundException("Catagorey ", "catagorey id", catagoreyId));
		List<Post> posts = new ArrayList<>();
//		List<PostDto> result = new ArrayList<>();
		for (PostDto postDto : postDtos) {
			Post post = this.modelMapper.map(postDto, Post.class);
			post.setImgName("deafult.png");
			post.setAddedDate(new Date());
			post.setUser(user);
			post.setCatagorey(catagorey);
			posts.add(post);

//		Post newPost = this.postRepo.save(post);
		}

		List<Post> savedPosts = this.postRepo.saveAll(posts);

		// ✅ Convert saved posts back to DTOs
		List<PostDto> result = savedPosts.stream().map(savedPost -> this.modelMapper.map(savedPost, PostDto.class))
				.collect(Collectors.toList());

		return result;

//		List<Post> savePost = this.postRepo.saveAll(posts);
//		for(Post savedPost : savePosts) {
//			result.add(this.modelMapper.map(savePost, PostDto.class));
//		}
//
//		return result;
	}

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// Get orignal filename
		String orignalFilename = file.getOriginalFilename();

		// validate and sanitize filename
		String santitizeFilename = santitizeFileName(orignalFilename);
		
		//generate unique file name 
		String finalFilename = UUID.randomUUID().toString()+"_"+santitizeFilename;
		
		//create full file path
		String filePath = path + File.separator+finalFilename;
		
		//create directory if it don't exist
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		//save the file
		Files.copy(file.getInputStream(),Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		
		return finalFilename;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws IOException {
		//first try the exact filename
		File file = new File(path+File.separator+fileName);
		if(file.exists()) {
			return new FileInputStream(file);
		}
		
		//if not Found, try to find with any extension
		String baseName = getFileBaseName(fileName);
		File foundFile = findFileWithAnyExtension(path, baseName);
		
		if(foundFile != null) {
			return new FileInputStream(foundFile);
		}
		
		throw new FileNotFoundException("File not found: "+ fileName);
	}

	@Override
	public String getContentType(String fileName) {
		
		return determineContentType(fileName);
	}

	private String santitizeFileName(String fileName) {
		if (fileName == null) {
			return UUID.randomUUID().toString();
		}

		// Remove path traversal characters and replace problematic characters

		fileName = fileName.replaceAll("\\.\\.", ""); // prevent path transversal
		fileName = fileName.replaceAll("[\\\\/.:*?\"<>|]", "_");// Replace invalid chars
		fileName = fileName.trim();

		// If filename is still empty, generate a UUID
		if (fileName.isEmpty()) {
			return UUID.randomUUID().toString();

		}

		return fileName;
	}

	private String ensureFileExtention(String fileName, String mineType) {
		if(fileName == null) {
			return UUID.randomUUID().toString()+".jpg";
		}
		
		if(fileName.contains(".")) {
			return fileName;
		}
		
		Map<String, String> minToExtention = new HashMap();
		minToExtention.put("image/jpeg",".jpg");
		minToExtention.put("image/jpg",".jpg");
		minToExtention.put("image/png",".png");
		minToExtention.put("image/gif",".gif");
		minToExtention.put("image/tiff",".tiff");
		minToExtention.put("image/webp",".webp");
		minToExtention.put("image/bpm",".bpm");
		minToExtention.put("image/svg+xml",".svg");
		
		String extension = minToExtention.getOrDefault(mineType, ".jpg");
		return fileName + extension;
		
		
	}

	private File findFileWithAnyExtension(String path , String baseName) {
		File directory = new File(path);
		if(!directory.exists()) {
			return null;
		}
		
		String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".tiff", ".tif", 
                ".webp", ".bmp", ".svg", ".ico", ".jfif", ".pjpeg", ".pjp"};
		
		for(String ext : extensions) {
			File file = new File(directory, baseName+ext);
			if(file.exists()) {
				return file;
			}
			
			// Also check with uppercase extension
			
			file = new File(directory, baseName+ext.toUpperCase());
			if(file.exists()) {
				return file;
			}
		}
		return null;
	}
	
	private String determineContentType(String fileName) {
		String extension = getFileExtension(fileName).toLowerCase();
		
		Map<String, String> extensionToMediaType = new HashMap<>();
		  extensionToMediaType.put("jpg", MediaType.IMAGE_JPEG_VALUE);
	        extensionToMediaType.put("jpeg", MediaType.IMAGE_JPEG_VALUE);
	        extensionToMediaType.put("png", MediaType.IMAGE_PNG_VALUE);
	        extensionToMediaType.put("gif", MediaType.IMAGE_GIF_VALUE);
	        extensionToMediaType.put("tiff", "image/tiff");
	        extensionToMediaType.put("tif", "image/tiff");
	        extensionToMediaType.put("webp", "image/webp");
	        extensionToMediaType.put("bmp", "image/bmp");
	        extensionToMediaType.put("svg", "image/svg+xml");
	        extensionToMediaType.put("ico", "image/x-icon");
	        extensionToMediaType.put("jfif", MediaType.IMAGE_JPEG_VALUE);
	        
	        return extensionToMediaType.getOrDefault(extension, MediaType.APPLICATION_OCTET_STREAM_VALUE);
	}
	
	private String getFileExtension(String fileName) {
		if(fileName == null || fileName .contains(".")) {
			return "";
			
		}
		
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	private String getFileBaseName(String fileName) {
		if(fileName==null||!fileName.contains(".")) {
			return fileName;
		}
		
		return fileName.substring(0,fileName.lastIndexOf('.'));
	}

}
