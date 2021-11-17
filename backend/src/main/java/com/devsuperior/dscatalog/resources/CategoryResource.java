package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController 
@RequestMapping (value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<?> findAll () {
		List<CategoryDTO> list = categoryService.findAll();			
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		CategoryDTO dto = categoryService.findById(id);			
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody CategoryDTO dto) {
		dto = categoryService.insert(dto);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
}
