package com.blooging.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// this class is used to transfer the data
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	private String name;
	private String email;
	private String password;
	private String about;

}
//UserDto => user Data Transfer Object