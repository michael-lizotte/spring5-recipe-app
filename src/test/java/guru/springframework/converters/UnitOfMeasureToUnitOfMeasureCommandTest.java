package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "desc";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setup() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        UnitOfMeasure uom = new UnitOfMeasure();

        uom.setId(ID_VALUE);
        uom.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(uom);

        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}
