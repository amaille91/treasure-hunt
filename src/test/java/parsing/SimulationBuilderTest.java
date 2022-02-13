package parsing;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import lexing.model.MapLineToken;
import parsing.exceptions.IllegalMapSizeException;

public class SimulationBuilderTest {
	
	@Test
	void initializing_builder_with_negative_sizes_should_result_in_IllegalMapSizeException_first_size() throws Exception {
		MapLineToken mapLineToken = new MapLineToken(-1, 10);
		
		assertThrows(IllegalMapSizeException.class, () -> new SimulationBuilder(mapLineToken));
	}

}
