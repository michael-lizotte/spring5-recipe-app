package guru.springframework.controllers.recipe;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    RecipeController controller;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();

        mock.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    public void getRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1l);

        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mock.perform(get("/recipes/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Some description");
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        recipesData.add(new Recipe());

        when(recipeService.findAll()).thenReturn(recipesData);
        ArgumentCaptor<Set<Recipe>> arg = ArgumentCaptor.forClass(Set.class);

        String view = controller.getRecipes(model);

        assertEquals("index", view);
        verify(model, times(1)).addAttribute(eq("recipes"), arg.capture());
        assertEquals(2, arg.getValue().size());
    }
}