package guru.springframework.controllers.recipe;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
    MockMvc mock;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
        mock = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testMockMvc() throws Exception {
        mock.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1l);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mock.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Some description");
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        recipesData.add(new Recipe());

        when(recipeService.findAll()).thenReturn(recipesData);
        ArgumentCaptor<Set<Recipe>> arg = ArgumentCaptor.forClass(Set.class);

        String view = controller.getRecipes(model);

        assertEquals("index", view);
        verify(model, times(1)).addAttribute(eq("recipes"), arg.capture());
        assertEquals(2, arg.getValue().size());
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mock.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mock.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/2"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mock.perform(get("/recipe/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}