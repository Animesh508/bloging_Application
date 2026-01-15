package com.blooging.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ApiResponce {
	
	
	private String message;
	private boolean scucess;
	public ApiResponce(String string, boolean b) {
		// TODO Auto-generated constructor stub
	}
	

}
