package com.blooging.blog.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException  extends RuntimeException{
	String resourcename;
	String fieldName;
	long fieldValue;
	public ResourceNotFoundException(String resourcename, String fieldName, long fieldValue) {
		super(String.format( "%s not dount %s : %l", resourcename, fieldName, fieldValue));
		this.resourcename = resourcename;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
}
