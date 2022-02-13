package parsing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static simulation.model.Orientation.EAST;
import static simulation.model.Orientation.SOUTH;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.exceptions.NoMapException;
import parsing.exceptions.TooManyMapsException;

public class ParserTest {
	
	@Test
	void parsing_tokens_without_a_map_token_should_result_in_NoMapException() throws Exception {
		Queue<LineToken> tokens = new LinkedList<>(Arrays.asList(
				new MountainLineToken(1, 1),
				new MountainLineToken(2, 3),
				new TreasureLineToken(1, 2, 2),
				new AdventurerLineToken("Adventurer1", 0, 1, SOUTH, new LinkedList<>()),
				new AdventurerLineToken("Adventurer2", 1, 0, EAST, new LinkedList<>())));
		
		assertThrows(NoMapException.class, () -> Parser.parse(tokens));
	}
	
	@Test
	void parsing_tokens_with_multiple_map_tokens_should_result_in_TooManyMapsException() throws Exception {
		Queue<LineToken> tokens = new LinkedList<>(Arrays.asList(
				new MountainLineToken(1, 1),
				new MountainLineToken(2, 3),
				new MapLineToken(4, 5),
				new TreasureLineToken(1, 2, 2),
				new AdventurerLineToken("Adventurer1", 0, 1, SOUTH, new LinkedList<>()),
				new MapLineToken(10, 10),
				new AdventurerLineToken("Adventurer2", 1, 0, EAST, new LinkedList<>())));
		
		assertThrows(TooManyMapsException.class, () -> Parser.parse(tokens));
	}

}
