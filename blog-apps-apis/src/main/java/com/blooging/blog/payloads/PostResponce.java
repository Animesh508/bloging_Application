package com.blooging.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponce {

	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;

	private boolean lastPage;

	public void setContent(List<PostDto> postDto) {
		// TODO Auto-generated method stub
		
	}

	public void setPageNumber(int number) {
		// TODO Auto-generated method stub
		
	}

	public void setPageSize(int size) {
		// TODO Auto-generated method stub
		
	}

	public void setTotalElements(long totalElements2) {
		// TODO Auto-generated method stub
		
	}

	public void setTotalPages(int totalPages2) {
		// TODO Auto-generated method stub
		
	}

	public void setLastPage(boolean last) {
		// TODO Auto-generated method stub
		
	}

}
