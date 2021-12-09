package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entites.Product;
import com.devsuperior.dscatalog.factories.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;
	
	private long existsId;
	private long nonExistsId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existsId = 1l;
		nonExistsId = 1000l;
		countTotalProducts = 26l;
	}
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull () {
		Product product = Factory.createProduct();
		product.setId(null);
		product = productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(product.getId(), countTotalProducts);
	}
	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		productRepository.deleteById(existsId);

		Optional<Product> opt = productRepository.findById(existsId);

		Assertions.assertFalse(opt.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, 
				() -> productRepository.deleteById(nonExistsId));
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Product> opt = productRepository.findById(existsId);

		Assertions.assertTrue(opt.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyObjectWhenIdExists() {
		Optional<Product> opt = productRepository.findById(nonExistsId);

		Assertions.assertFalse(opt.isPresent());
	}
}
