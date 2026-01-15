package com.blooging.blog.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="catagorey")
public class Catagorey {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="catagory")
	private String catagoryName;
	@Column(name="catagoryDescription")
	private String catagoryDescription;
	
	
	@OneToMany(mappedBy = "catagorey", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> posts= new ArrayList<>();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCatagoryName() {
		return catagoryName;
	}


	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}


	public String getCatagoryDescription() {
		return catagoryDescription;
	}


	public void setCatagoryDescription(String catagoryDescription) {
		this.catagoryDescription = catagoryDescription;
	}

	

}
