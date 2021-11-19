package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.entites.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		List<Product> list = productRepository.findAll();	
		
		return list.stream()
				.map(x -> new ProductDTO(x))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)   
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {  // metodo para buscar todas as categorias
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> opt= productRepository.findById(id);
		Product product = opt.orElseThrow(() -> new ResourceNotFoundException("Erro ao recuperar Categoria"));
		return new ProductDTO(product, product.getCategories());				
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product obj = new Product();
		copyDtoToEntity(dto, obj);
		productRepository.save(obj);
		return new ProductDTO(obj);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product obj = productRepository.getOne(id);
			copyDtoToEntity(dto, obj);
			
			productRepository.save(obj);
			return new ProductDTO(obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product obj) {
		obj.setName(dto.getName());
		obj.setDescription(dto.getDescription());
		obj.setPrice(dto.getPrice());
		obj.setImgUrl(dto.getImgUrl());
		obj.setDate(dto.getDate());
		obj.getCategories().clear();
		
		for(CategoryDTO catDto: dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			obj.getCategories().add(category);
		}
	}

	public void delete(Long id) {
		try {			
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException di) {
			throw new DataBaseException("Integrity violation: " + id);
		} 
	}
	
	
}
