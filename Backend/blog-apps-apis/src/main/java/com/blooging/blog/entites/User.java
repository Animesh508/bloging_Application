package com.blooging.blog.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "user_name", nullable=false, length=100)
	private String name;
	
	@Column(name= "user_email", nullable=false, length=100, unique=true)
	private String email;
	@Column(name= "user_password", nullable=false, length=100)
	private String password;
	@Column(name= "about_user", nullable=true, length=1000)
	private String about;

}
//we are not going to use entity for putting data to data base we are using it to only crearte the tables.
// where as to transfer the data we are going to use UserDto
