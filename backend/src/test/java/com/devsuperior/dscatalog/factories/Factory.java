package com.devsuperior.dscatalog.factories;

import java.time.Instant;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.entites.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 2500.0, "https://img.com.br/img.jpg", Instant.parse("2021-03-13T18:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static ProductDTO createProductDTO(Long id) {
		ProductDTO dto = createProductDTO();
		dto.setId(id);
		return dto;
	}
	
	public static Category createCategory() {
		Category category = new Category(1L, "Eletronics");
		return category;
	}
}
