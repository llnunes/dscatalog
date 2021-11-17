package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

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
	public CategoryDTO findById(Long id) {
		Optional<Category> opt= categoryRepository.findById(id);
		Category category = opt.orElseThrow(() -> new EntityNotFoundException("Erro ao recuperar Categoria"));
		return new CategoryDTO(category);				
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category c = new Category();
		c.setName(dto.getName());
		categoryRepository.save(c);
		return new CategoryDTO(c);
	}
}
