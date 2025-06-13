package com.blooging.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blooging.blog.payloads.ApiResponce;
import com.blooging.blog.payloads.UserDto;
import com.blooging.blog.services.UserService;

import jakarta.validation.Valid;

@RestController // it indicates that this class is a RESTful web service  controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired //used for automatic dependency injection or it asks for bean for an object to inject dependency 
	private UserService userService;

	// POST-CREATE USER
	// created UserDto to not directly expose User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}

	// PUT - UPDATE USER
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
		UserDto updateUserDto = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUserDto);

	}

	// DELETE - DELETE USER
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponce> DeleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("user deleated ScuccessFully", true), HttpStatus.OK);

	}

	// GET - GET USER
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		return ResponseEntity.ok(this.userService.getAllUser());
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId){
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
		
	}

}
