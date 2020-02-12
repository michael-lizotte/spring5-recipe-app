package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }

        Ingredient ing = new Ingredient();

        ing.setId(ingredientCommand.getId());
        ing.setDescription(ingredientCommand.getDescription());
        ing.setAmount(ingredientCommand.getAmount());
        ing.setUom(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));

        return ing;
    }
}
