package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();	
		
		return list.stream()
				.map(x -> new CategoryDTO(x))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)   
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {  // metodo para buscar todas as categorias
		Page<Category> list = categoryRepository.findAll(pageable);
		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> opt= categoryRepository.findById(id);
		Category category = opt.orElseThrow(() -> new ResourceNotFoundException("Erro ao recuperar Categoria"));
		return new CategoryDTO(category);				
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category c = new Category();
		c.setName(dto.getName());
		categoryRepository.save(c);
		return new CategoryDTO(c);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category cat = categoryRepository.getOne(id);
			cat.setName(dto.getName());
			
			categoryRepository.save(cat);
			return new CategoryDTO(cat);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	public void delete(Long id) {
		try {			
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException di) {
			throw new DataBaseException("Integrity violation: " + id);
		} 
	}
	
	
}
