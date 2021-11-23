package com.mugdha.multiImages.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product_images")
public class ProductImages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int image_id;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "product_id")	
    private Product product;
	
	@Column(name="name")
	private String name;
	
	@Column(name="image_urls")
	private String imageUrls;
	
	
	public ProductImages () {};
	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
}