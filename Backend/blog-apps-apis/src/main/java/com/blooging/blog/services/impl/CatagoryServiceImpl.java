package com.blooging.blog.services.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.exceptions.ResourceNotFoundException;
import com.blooging.blog.exceptions.catagoryAlreadyExistException;
import com.blooging.blog.payloads.CatagoreyDTO;
import com.blooging.blog.repositories.CatagoreyRepo;
import com.blooging.blog.services.CatagoryService;


@Service
public class CatagoryServiceImpl implements CatagoryService {
	
	@Autowired
	private CatagoreyRepo catagoreyRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	

	@Override
	public CatagoreyDTO createCatagory(CatagoreyDTO catogoreyDto) {
		Catagorey exestingCatagorey = catagoreyRepo.findByCatagoryName(catogoreyDto.getCatagoryName());
		if(exestingCatagorey != null) {
			throw new catagoryAlreadyExistException("catagorey with name "+ catogoreyDto.getCatagoryName() + " already exsist.");
		}
		Catagorey catagorey = this.dtoToCatagorey(catogoreyDto);
		
		Catagorey saveCatagorey = this.catagoreyRepo.save(catagorey);
		return this.catagoryToDto(saveCatagorey);
	}

	@Override
	public CatagoreyDTO updateCatagory(CatagoreyDTO catagoryDto, Integer CatagoreyID) {
		Catagorey catagory = this.catagoreyRepo.findById(CatagoreyID)
				.orElseThrow(()-> new ResourceNotFoundException("Catagorey", "Id", CatagoreyID));
		catagory.setCatagoryName(catagoryDto.getCatagoryName());
		catagory.setCatagoryDescription(catagoryDto.getCatagoryDescription());
		
		Catagorey updateCatagory = this.catagoreyRepo.save(catagory);
		CatagoreyDTO saveCatagory = this.catagoryToDto(updateCatagory);
		return saveCatagory;
	}

	@Override
	public CatagoreyDTO getCatagorybyName(String catagory) {
		Catagorey catagorey = catagoreyRepo.findByCatagoryName(catagory);
		if(catagorey == null) {
			throw new ResourceNotFoundException("Catagory", "name", catagorey);
			}
		return this.catagoryToDto(catagorey);
	}

	@Override
	public List<CatagoreyDTO> getAllCatagorey() {
		List<Catagorey> catagoreys = this.catagoreyRepo.findAll();
		List<CatagoreyDTO> catagoreyDTOs = catagoreys.stream().map(catagorey->this.catagoryToDto(catagorey)).collect(Collectors.toList());
 		return catagoreyDTOs;
	}

	@Override
	public void deleteCatagorey(String catagory) {
		// TODO Auto-generated method stub
		
	}
	

	private Catagorey dtoToCatagorey(CatagoreyDTO catagoreyDTO) {
		Catagorey catagory = this.modelMapper.map(catagoreyDTO, Catagorey.class);
		return catagory;
	}
	
	
	private CatagoreyDTO catagoryToDto(Catagorey catagorey) {
		CatagoreyDTO catagoryDto = this.modelMapper.map(catagorey, CatagoreyDTO.class);
		return catagoryDto;
	}

	@Override
	public CatagoreyDTO getCatagory(Integer catagory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
