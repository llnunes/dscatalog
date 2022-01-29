package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);		
		Assertions.assertEquals(countTotalProducts -1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProductDTO> page = service.findAllPaged(0L, "",pageRequest);
		
		Assertions.assertNotNull(page);	
		
		Assertions.assertFalse(page.isEmpty());	
		Assertions.assertEquals(0, page.getNumber());	
		Assertions.assertEquals(10, page.getSize());	
		Assertions.assertEquals(countTotalProducts, page.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		PageRequest pageRequest = PageRequest.of(50, 10);
		Page<ProductDTO> page = service.findAllPaged(0L, "",pageRequest);
		
		Assertions.assertNotNull(page);	
		
		Assertions.assertTrue(page.isEmpty());	
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		Page<ProductDTO> page = service.findAllPaged(0L, "",pageRequest);
		
		Assertions.assertNotNull(page);	
		
		Assertions.assertFalse(page.isEmpty());	
		Assertions.assertEquals("Macbook Pro", page.getContent().get(0).getName());	
		Assertions.assertEquals("PC Gamer", page.getContent().get(1).getName());	
		Assertions.assertEquals("PC Gamer Alfa", page.getContent().get(2).getName());	

	}
}
