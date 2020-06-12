package distributedsystems.facade;

import controller.domainmodel.RecipeStrategy;
import datasource.datamapper.RecipeDataMapper;
import distributedsystems.dto.RecipeDTO;
import model.Recipe;

//Since this pattern has not been used in the real action, there are some minor modifications to avoid from errors.
public class RecipeRemoteFacade {
	
	public String getRecipe(int recipeId) {
		Recipe recipeReaderObject = new Recipe(recipeId);
		RecipeStrategy rs = new RecipeStrategy(recipeReaderObject);
		
		//Since the pattern is not connected to rest of the system, we are sending Recipe Object instead of RecipeDTO.
		//readPost() returns a serialized DTO object (which means that it is a json string).
		//that is why we return a string
		String recipe = rs.readPost();
		return recipe;
	}
	
	public RecipeDTO getRecipeJSON(int recipeId) {
		Recipe recipeReaderObject = new Recipe(recipeId);
		RecipeStrategy rs = new RecipeStrategy(recipeReaderObject);
		
		//Since the pattern is not connected to rest of the system, we are sending Recipe Object instead of RecipeDTO.
		//readPost() returns a serialized DTO object (which means that it is a json string).
		String recipe = rs.readPost();
		
		RecipeDTO rdto = RecipeDTO.deserialize(recipe);
		
		return rdto;
	}
	
	public void updateRecipe(RecipeDTO rdto) {
		//Since the pattern is not connected to rest of the system, we are sending Recipe Object instead of RecipeDTO.
		//if the system will be distributed to real separate components, the update function in RecipeStrategy should be converted to accept Recipe DTO
		//To convert RecipeDTO to Recipe:
		Recipe recipe = new Recipe(rdto.getRecipeId(), rdto.getUserName(), rdto.getRecipeTitle(), 
				rdto.getRecipeDescription(), rdto.getCookingTime(), rdto.getCost(), rdto.getRating(),
				rdto.getCategoryName(), rdto.getVersion(), rdto.getModifiedBy(), 
				rdto.getIngredientList(), rdto.getCommentList());
		
		RecipeStrategy rs = new RecipeStrategy(recipe);
		int updateResult = rs.editPost();
	}
	
	public void updateRecipeJSON(String json) {
		RecipeDTO rdto = RecipeDTO.deserialize(json);
		
		//Since the pattern is not connected to rest of the system, we are sending Recipe Object instead of RecipeDTO.
		//if the system will be distributed to real separate components, the update function in RecipeStrategy should be converted to accept Recipe DTO
		//Thus, we will send a recipe function, instead of RecipeDTO again.
		Recipe recipe = new Recipe(rdto.getRecipeId(), rdto.getUserName(), rdto.getRecipeTitle(), 
				rdto.getRecipeDescription(), rdto.getCookingTime(), rdto.getCost(), rdto.getRating(),
				rdto.getCategoryName(), rdto.getVersion(), rdto.getModifiedBy(), 
				rdto.getIngredientList(), rdto.getCommentList());
		
		RecipeStrategy rs = new RecipeStrategy(recipe);
		int updateResult = rs.editPost();
	}
}
