package com.blooging.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blooging.blog.payloads.ApiResponce;
import com.blooging.blog.payloads.CatagoreyDTO;
import com.blooging.blog.services.CatagoryService;

@RestController
@RequestMapping("/api/catagorey")
public class CatagoryController {
	
	@Autowired
	private CatagoryService catagoryService;
	
	@PostMapping("/")
	public ResponseEntity<CatagoreyDTO> createCatagorey(@RequestBody CatagoreyDTO catagoreyDTO){
		
		CatagoreyDTO createCatagoryDto = this.catagoryService.createCatagory(catagoreyDTO);
		return new ResponseEntity<>(createCatagoryDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/{catagoryName}") //get single category
	public ResponseEntity<CatagoreyDTO> getSingleCatagorey(@PathVariable("catagoryName")  String catagoryName){
		return ResponseEntity.ok(this.catagoryService.getCatagorybyName(catagoryName));
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<CatagoreyDTO>> getAllCatagorey(){
		return ResponseEntity.ok(this.catagoryService.getAllCatagorey()); 
	}
	

}
