package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();

        command.setId(ingredient.getId());
        if (ingredient.getRecipe() != null)
            command.setRecipeId(ingredient.getRecipe().getId());
        command.setDescription(ingredient.getDescription());
        command.setAmount(ingredient.getAmount());
        command.setUnitOfMeasure(uomConverter.convert(ingredient.getUom()));

        return command;
    }
}
