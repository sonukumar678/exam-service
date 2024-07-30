package com.exam.examserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.models.exam.Category;
import com.exam.examserver.services.CategoryService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//add category
	@PostMapping("/")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		Category category1 = this.categoryService.addCategory(category);
		return ResponseEntity.ok(category1);
	}
	
	//get Category
	@GetMapping("/{categoryId}")
	public Category getCategory(@PathVariable Long categoryId){
		return this.categoryService.getCategory(categoryId);
	}
	
	//get All category
	@GetMapping("/")
	public ResponseEntity<?> getCategories(){
		return ResponseEntity.ok(this.categoryService.getCategories());
	}
	
	//update Category
	@PutMapping("/")
	public Category updateCategory(@RequestBody Category category){
		return this.categoryService.updateCategory(category);
	}
	
	//Delete Category
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable Long categoryId) {
		this.categoryService.deleteCategory(categoryId);
	}
	
	
}
