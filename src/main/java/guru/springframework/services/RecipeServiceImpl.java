package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public <S extends Recipe> Iterable<S> saveAll(Iterable<S> recipes) {
        return recipeRepository.saveAll(recipes);
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return recipeRepository.existsById(id);
    }

    @Override
    public Iterable<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Iterable<Recipe> findAllById(Iterable<Long> ids) {
        return recipeRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return recipeRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    @Override
    public void deleteAll(Iterable<? extends Recipe> recipes) {
        recipeRepository.deleteAll(recipes);
    }

    @Override
    public void deleteAll() {
        recipeRepository.deleteAll();
    }
}
