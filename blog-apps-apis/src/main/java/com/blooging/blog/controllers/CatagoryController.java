package com.blooging.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blooging.blog.entites.Catagorey;
import com.blooging.blog.payloads.ApiResponce;
import com.blooging.blog.payloads.CatagoreyDTO;
import com.blooging.blog.services.CatagoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catagorey")
public class CatagoryController {
	
	@Autowired
	private CatagoryService catagoryService;
	
	@PostMapping("/") //creating the repo
	public ResponseEntity<CatagoreyDTO> createCatagorey(@Valid @RequestBody CatagoreyDTO catagoreyDTO){
		
		CatagoreyDTO createCatagoryDto = this.catagoryService.createCatagory(catagoreyDTO);
		return new ResponseEntity<>(createCatagoryDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/{catagoryName}") //get single category
	public ResponseEntity<CatagoreyDTO> getSingleCatagorey(@PathVariable("catagoryName")  String catagoryName){
		return ResponseEntity.ok(this.catagoryService.getCatagorybyName(catagoryName));
	}
	
	
	@GetMapping("/") //Getting list of category
	public ResponseEntity<List<CatagoreyDTO>> getAllCatagorey(){
		return ResponseEntity.ok(this.catagoryService.getAllCatagorey()); 
	}
	
	@PutMapping("/{CatagoreyID}") //updating
	public ResponseEntity<CatagoreyDTO> updateCatagorey(@Valid @RequestBody CatagoreyDTO catagoryDto, @PathVariable("CatagoreyID") Integer CatagoreyID){
		CatagoreyDTO updateCatagoreyDto = this.catagoryService.updateCatagory(catagoryDto, CatagoreyID);
		return ResponseEntity.ok(updateCatagoreyDto);
	}
	
	@DeleteMapping("/{catagoryName}") //deleting
	public ResponseEntity<ApiResponce> DeleteCatagorey(@PathVariable("catagoryName") String catagoryName){
		this.catagoryService.deleteCatagorey(catagoryName);
		return new ResponseEntity<ApiResponce>(new ApiResponce("catagorey deleated scuscessfuly", true), HttpStatus.OK);
	}
	

}
