package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import java.util.Set;

public interface RecipeService {
    Recipe findById(Long id);
    Set<Recipe> findAll();

    Long count();

    Recipe save(Recipe recipe);

    void deleteById(Long id);
    void delete(Recipe recipe);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
