package com.mugdha.multiImages.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
public class Product {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int product_id;

	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="product",cascade = CascadeType.ALL)
    private Set<ProductImages> productIamges;
    
    
	@Column(name = "name")
	private String name;

	public Product() {

	}
	
	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public Set<ProductImages> getProductIamges() {
		return productIamges;
	}

	public void setProductIamges(Set<ProductImages> productIamges) {
		this.productIamges = productIamges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product(String name) {
		this.name = name;
	}

}
