package com.blooging.blog.services;

import java.util.List;

import com.blooging.blog.entites.User;
import com.blooging.blog.payloads.UserDto;

public interface UserService {

	// insted of using User class to return data we will use UserDto to return data
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUser();

	void deleteUser(Integer userId);

}
