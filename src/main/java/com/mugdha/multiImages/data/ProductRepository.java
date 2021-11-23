package com.mugdha.multiImages.data;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.mugdha.multiImages.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>
{

}