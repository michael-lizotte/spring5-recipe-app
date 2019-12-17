package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommands implements Converter<Notes, NotesCommand> {
    @Override
    public NotesCommand convert(Notes notes) {
        if (notes == null) {
            return null;
        }

        NotesCommand command = new NotesCommand();

        command.setId(notes.getId());
        command.setRecipeNotes(notes.getNote());

        return command;
    }
}
