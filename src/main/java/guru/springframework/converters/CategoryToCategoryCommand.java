package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Override
    public CategoryCommand convert(Category category) {
        if (category == null) {
            return null;
        }

        final CategoryCommand catCommand = new CategoryCommand();

        catCommand.setId(category.getId());
        catCommand.setDescription(category.getDescription());

        return catCommand;
    }
}
