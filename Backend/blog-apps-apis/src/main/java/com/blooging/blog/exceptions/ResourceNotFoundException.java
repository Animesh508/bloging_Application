package com.blooging.blog.exceptions;

import com.blooging.blog.entites.Catagorey;

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
	Catagorey Value;
	public ResourceNotFoundException(String resourcename, String fieldName, long fieldValue) {
		super(String.format( "%s not douot %s : %s", resourcename, fieldName, fieldValue));
		this.resourcename = resourcename;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourcename, String fieldName, Catagorey Value) {
		super(String.format("%s not donot %s : %s", resourcename, fieldName, Value));
		this.resourcename = resourcename;
		this.fieldName = fieldName;
		this.Value = Value;
	}
	
	
	
}
