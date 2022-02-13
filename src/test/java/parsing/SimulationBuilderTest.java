package parsing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static simulation.model.Orientation.EAST;
import static simulation.model.Orientation.SOUTH;

import java.util.LinkedList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.exceptions.AlreadyOccupiedSpaceException;
import parsing.exceptions.IllegalMapSizeException;
import parsing.exceptions.NoMapException;
import parsing.exceptions.OutboundTerrainException;
import parsing.exceptions.TooManyMapsException;
import simulation.model.AdventurerAction;

public class SimulationBuilderTest {
	
	@Test
	void parsing_tokens_without_a_map_token_should_result_in_NoMapException() throws Exception {
		List<LineToken> tokens = Lists.list(new MountainLineToken(1, 1),
				new MountainLineToken(2, 3),
				new TreasureLineToken(1, 2, 2),
				new AdventurerLineToken("Adventurer1", 0, 1, SOUTH, new LinkedList<>()),
				new AdventurerLineToken("Adventurer2", 1, 0, EAST, new LinkedList<>()));
		
		assertThrows(NoMapException.class, () -> new Parser(tokens));
	}
	
	@Test
	void parsing_tokens_with_multiple_map_tokens_should_result_in_TooManyMapsException() throws Exception {
		List<LineToken> tokens = Lists.list(new MountainLineToken(1, 1),
				new MountainLineToken(2, 3),
				new MapLineToken(4, 5),
				new TreasureLineToken(1, 2, 2),
				new AdventurerLineToken("Adventurer1", 0, 1, SOUTH, new LinkedList<>()),
				new MapLineToken(10, 10),
				new AdventurerLineToken("Adventurer2", 1, 0, EAST, new LinkedList<>()));
		
		assertThrows(TooManyMapsException.class, () -> new Parser(tokens));
	}

	@Nested
	class StrictlyPositiveMapSizes {

		@Test
		void initializing_builder_with_negative_sizes_should_result_in_IllegalMapSizeException_first_size()
				throws Exception {
			MapLineToken mapLineToken = new MapLineToken(-1, 10);

			assertThrows(IllegalMapSizeException.class, () -> new Parser(Lists.list(mapLineToken)));
		}

		@Test
		void initializing_builder_with_negative_sizes_should_result_in_IllegalMapSizeException_second_size()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(3, -1));

			assertThrows(IllegalMapSizeException.class, () -> new Parser(tokens));
		}

		@Test
		void initializing_builder_with_zero_size_should_result_in_IllegalMapSizeException_first_size()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(0, 1));

			assertThrows(IllegalMapSizeException.class, () -> new Parser(tokens));
		}

		@Test
		void initializing_builder_with_zero_sizes_should_result_in_IllegalMapSizeException_second_size()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(3, 0));

			assertThrows(IllegalMapSizeException.class, () -> new Parser(tokens));
		}

	}

	@Nested
	class TerrainInsideMap {

		@Test
		void a_mountain_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15), new MountainLineToken(-1, 3));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}

		@Test
		void a_mountain_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15), new MountainLineToken(9, -3));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}

		@Test
		void a_treasure_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15), new TreasureLineToken(-9, 3, 1));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}

		@Test
		void a_treasure_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15), new TreasureLineToken(0, -1, 1));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}

		@Test
		void a_adventurer_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new AdventurerLineToken("adventurer's name", -1, 1, SOUTH, new LinkedList<AdventurerAction>()));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}

		@Test
		void a_adventurer_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate()
				throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new AdventurerLineToken("adventurer's name", -1, 1, SOUTH, new LinkedList<AdventurerAction>()));

			assertThrows(OutboundTerrainException.class, () -> new Parser(tokens));
		}
	}

	@Nested
	class TerrainCannotStack {

		@Test
		void a_mountain_on_top_of_a_treasure_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> mapLineToken = Lists.list(new MapLineToken(10, 15),
					new TreasureLineToken(2, 3, 1),
					new MountainLineToken(2, 3));

			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(mapLineToken));
		}
		
		@Test
		void a_mountain_on_top_of_an_adventurer_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15), 
					new AdventurerLineToken("adventurer's name", 2, 1, SOUTH, new LinkedList<AdventurerAction>()),
					new MountainLineToken(2, 1));
			
			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(tokens));
		}
		
		@Test
		void a_treasure_on_top_of_a_mountain_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new MountainLineToken(2, 3),
					new TreasureLineToken(2, 3, 1));
			
			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(tokens));
		}
		
		@Test
		void a_treasure_on_top_of_an_adventurer_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new AdventurerLineToken("adventurer's name", 2, 1, SOUTH, new LinkedList<AdventurerAction>()),
					new TreasureLineToken(2, 1, 1));
			
			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(tokens));
		}
		
		@Test
		void an_adventuer_on_top_of_a_mountain_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new MountainLineToken(2, 3),
					new AdventurerLineToken("adventurer's name", 2, 3, SOUTH, new LinkedList<AdventurerAction>()));
			
			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(tokens));
		}
		
		@Test
		void an_adventurer_on_top_of_a_treasure_should_result_in_AlreadyOccupiedSpaceException() throws Exception {
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 15),
					new TreasureLineToken(2, 1, 1),
					new AdventurerLineToken("adventurer's name", 2, 1, SOUTH, new LinkedList<AdventurerAction>()));
			
			assertThrows(AlreadyOccupiedSpaceException.class, () -> new Parser(tokens));
		}
	}

}
