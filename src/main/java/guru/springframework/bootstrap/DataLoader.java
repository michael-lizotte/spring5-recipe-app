package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private RecipeService recipeService;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeService recipeService,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeService = recipeService;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws RuntimeException {
        try {
            if (((Collection<?>)recipeService.findAll()).size() == 0) {
                loadData();
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private void loadData() throws RuntimeException {
        System.out.println("Loading data...");

        // Loading Units of measures
        Optional<UnitOfMeasure> teaSpoon = unitOfMeasureRepository.findByDescription("teaspoon of ");
        Optional<UnitOfMeasure> tableSpoon = unitOfMeasureRepository.findByDescription("tablespoon of ");
//        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");
//        Optional<UnitOfMeasure> pinch = unitOfMeasureRepository.findByDescription("Pinch");
//        Optional<UnitOfMeasure> ounce = unitOfMeasureRepository.findByDescription("Ounce");
        Optional<UnitOfMeasure> whole = unitOfMeasureRepository.findByDescription(" ");

        if (!teaSpoon.isPresent() || !tableSpoon.isPresent() || !whole.isPresent()) {
            throw new RuntimeException("Missing unit of measure");
        }

        // Loading categories
        Category mexican = new Category();
        mexican.setDescription("mexican");

        Category vegan = new Category();
        vegan.setDescription("vegan");

        Category avocado = new Category();
        avocado.setDescription("avocado");

        // Loading ingredients for the guacamole
        Ingredient avocados = new Ingredient();
        avocados.setDescription("avocados");
        avocados.setAmount(new BigDecimal(2));
        avocados.setUom(whole.get());

        Ingredient kosherSalt = new Ingredient();
        kosherSalt.setDescription("kosher salt");
        kosherSalt.setAmount(new BigDecimal("0.5"));
        kosherSalt.setUom(teaSpoon.get());

        Ingredient freshLimeJuice = new Ingredient();
        freshLimeJuice.setDescription("fresh lime or lemon Juice");
        freshLimeJuice.setAmount(new BigDecimal(1));
        freshLimeJuice.setUom(tableSpoon.get());

        Notes notes = new Notes();
        notes.setNote("Some note");

        // Loading Recipe
        Recipe guacamoleRecipe = new Recipe();

        avocados.setRecipe(guacamoleRecipe);
        kosherSalt.setRecipe(guacamoleRecipe);
        freshLimeJuice.setRecipe(guacamoleRecipe);

        guacamoleRecipe.setName("Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamoleRecipe.setNotes(notes);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("Some source");
        guacamoleRecipe.setUrl("http://www.superGuacamole.com");

        guacamoleRecipe.getIngredients().add(avocados);
        guacamoleRecipe.getIngredients().add(kosherSalt);
        guacamoleRecipe.getIngredients().add(freshLimeJuice);

        notes.setRecipe(guacamoleRecipe);

        mexican.getRecipes().add(guacamoleRecipe);
        vegan.getRecipes().add(guacamoleRecipe);
        avocado.getRecipes().add(guacamoleRecipe);

        guacamoleRecipe.getCategories().add(mexican);
        guacamoleRecipe.getCategories().add(vegan);
        guacamoleRecipe.getCategories().add(avocado);

        recipeService.save(guacamoleRecipe);
    }
}
