package com.blooging.blog.payloads;

import java.util.Date;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.entites.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String imgName;
	private Date addDate;

	private CatagoreyDTO catagorey;

	private UserDto user;


}
