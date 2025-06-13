package com.blooging.blog.services;

import java.util.List;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.payloads.CatagoreyDTO;

public interface CatagoryService {
	
	CatagoreyDTO createCatagory(CatagoreyDTO catogoreyDto);
	
	CatagoreyDTO updateCatagory(CatagoreyDTO catagoryDto, Integer CatagoreyID);
	
	CatagoreyDTO getCatagorybyName(String catagory);
	
	List<CatagoreyDTO> getAllCatagorey();
	
	void deleteCatagorey (String catagory);
	
	CatagoreyDTO getCatagory(Integer catagory);

}
