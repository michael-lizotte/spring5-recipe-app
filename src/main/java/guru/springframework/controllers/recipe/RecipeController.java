package guru.springframework.controllers.recipe;

import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private RecipeService recipeServiceImpl;

    public RecipeController(RecipeService recipeServiceImpl) {
        this.recipeServiceImpl = recipeServiceImpl;
    }

    @RequestMapping("/recipes")
    public String getRecipes(Model model) {

        model.addAttribute("recipes", recipeServiceImpl.findAll());

        return "recipe/index";
    }
}
