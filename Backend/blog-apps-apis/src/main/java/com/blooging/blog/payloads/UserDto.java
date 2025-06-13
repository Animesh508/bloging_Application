package com.blooging.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// this class is used to transfer the data
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min = 4, message = "should be min more than 4 characters ")
	private String name;
	@Email(message="Email is address is not valid!")
	private String email;
	@NotNull
	@Size(min=4, max=10, message="password must be min of 4 char and max of 10 char")
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String password;
	@NotNull
	private String about;

}
//UserDto => user Data Transfer Object