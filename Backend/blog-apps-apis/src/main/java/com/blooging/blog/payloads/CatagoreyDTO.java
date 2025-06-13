package com.blooging.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CatagoreyDTO {
	
	private Integer Id;
	private String catagoryName;
	private String catagoryDescription;

}
