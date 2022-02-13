package printing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import simulation.model.Adventurer;
import simulation.model.Mountain;
import simulation.model.Orientation;
import simulation.model.Position;
import simulation.model.Treasure;

public class PrinterTest {

	@Test
	void printer_should_always_start_with_a_map_line() throws Exception {
		List<String> lines = Printer.toStrings(3, 4, Map.of());
		
		assertThat(lines).startsWith("C - 3 - 4");
	}
	
	@Test
	void mountains_should_be_printed_first_in_order_of_their_position() throws Exception {
		List<String> lines = Printer.toStrings(3, 4,
				Map.of( new Position(2, 3), new Mountain(),
						new Position(2, 2), new Adventurer("Adventurer", Orientation.EAST, List.of(), 0, 0),
						new Position(1, 2), new Treasure(2),
						new Position(0, 0), new Mountain()));
		
		assertThat(lines).startsWith("C - 3 - 4", "M - 0 - 0", "M - 2 - 3");
	}
	
	@Test
	void treasures_should_be_printed_after_mountains_in_order_of_their_position() throws Exception {
		List<String> lines = Printer.toStrings(3, 4,
				Map.of( new Position(2, 3), new Mountain(),
						new Position(2, 2), new Adventurer("Adventurer", Orientation.EAST, List.of(), 0, 0),
						new Position(1, 2), new Treasure(2),
						new Position(0, 0), new Mountain(),
						new Position(0, 2), new Treasure(1)));
		
		assertThat(lines).startsWith("C - 3 - 4", "M - 0 - 0", "M - 2 - 3", "T - 0 - 2 - 1", "T - 1 - 2 - 2");
	}
}
