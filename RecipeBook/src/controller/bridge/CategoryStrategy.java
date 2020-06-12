package controller.bridge;

import java.util.ArrayList;
import java.util.List;

import datasource.datamapper.CategoryDataMapper;
import model.Category;

// This class (a domain layer member) is responsible for the communication between CategoryDataMapper and related servlets
public class CategoryStrategy {
	
	public CategoryStrategy() {
		
	}
	
	//This function is for reading all the categories to display on the screen during the recipe creation and update
	public List<Category> readAllCategories() {
		CategoryDataMapper cdm = new CategoryDataMapper();
		List<Category> allCategories = new ArrayList<Category>();
		allCategories = cdm.readAll();
		return allCategories;
		
	}
}
