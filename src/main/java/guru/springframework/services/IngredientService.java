package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {
    Set<Ingredient> listIngredients();
    IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
