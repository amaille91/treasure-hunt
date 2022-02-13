package parsing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static simulation.model.Orientation.SOUTH;

import java.util.LinkedList;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lexing.model.AdventurerLineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.exceptions.IllegalMapSizeException;
import parsing.exceptions.OutboundTerrainException;
import simulation.model.AdventurerAction;

public class SimulationBuilderTest {
	
	@Nested
	class StrictlyPositiveMapSizes {

		@Test
		void initializing_builder_with_negative_sizes_should_result_in_IllegalMapSizeException_first_size() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(-1, 10);
			
			assertThrows(IllegalMapSizeException.class, () -> new SimulationBuilder(mapLineToken));
		}
		
		@Test
		void initializing_builder_with_negative_sizes_should_result_in_IllegalMapSizeException_second_size() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(3, -1);
			
			assertThrows(IllegalMapSizeException.class, () -> new SimulationBuilder(mapLineToken));
		}
		
		@Test
		void initializing_builder_with_zero_size_should_result_in_IllegalMapSizeException_first_size() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(0, 10);
			
			assertThrows(IllegalMapSizeException.class, () -> new SimulationBuilder(mapLineToken));
		}
		
		@Test
		void initializing_builder_with_zero_sizes_should_result_in_IllegalMapSizeException_second_size() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(3, 0);
			
			assertThrows(IllegalMapSizeException.class, () -> new SimulationBuilder(mapLineToken));
		}
		
	}
	
	@Nested
	class TerrainInsideMap {
		
		@Test
		void a_mountain_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new MountainLineToken(-1, 3)));
		}
		
		@Test
		void a_mountain_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new MountainLineToken(9, -3)));
		}
		
		@Test
		void a_treasure_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new TreasureLineToken(-9, 3, 1)));
		}
		
		@Test
		void a_treasure_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new TreasureLineToken(0, -1, 1)));
		}
		
		@Test
		void a_adventurer_with_strictly_negative_position_should_result_in_OutboundTerrainException_first_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new AdventurerLineToken("adventurer's name", -1, 1, SOUTH, new LinkedList<AdventurerAction>())));
		}
		
		@Test
		void a_adventurer_with_strictly_negative_position_should_result_in_OutboundTerrainException_second_coordinate() throws Exception {
			MapLineToken mapLineToken = new MapLineToken(10, 15);
			
			SimulationBuilder builder = new SimulationBuilder(mapLineToken);
			
			assertThrows(OutboundTerrainException.class, () -> builder.withLine(new AdventurerLineToken("adventurer's name", -1, 1, SOUTH, new LinkedList<AdventurerAction>())));
		}
	}
	

}
