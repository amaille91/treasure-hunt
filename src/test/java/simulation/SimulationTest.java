package simulation;

import static org.assertj.core.api.Assertions.assertThat;
import static simulation.model.AdventurerAction.ADVANCE;
import static simulation.model.AdventurerAction.TURN_LEFT;
import static simulation.model.AdventurerAction.TURN_RIGHT;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.Parser;
import simulation.model.Adventurer;
import simulation.model.AdventurerAction;
import simulation.model.Mountain;
import simulation.model.Orientation;
import simulation.model.Position;
import simulation.model.Terrain;
import simulation.model.Treasure;

public class SimulationTest {
	
	@Test
	void the_simulation_should_give_back_coherent_results_with_the_example() throws Exception {
		List<AdventurerAction> listOfActions = Lists.list(ADVANCE, ADVANCE, TURN_RIGHT, ADVANCE, TURN_RIGHT, ADVANCE, TURN_LEFT, TURN_LEFT, ADVANCE);
		Map<Position, Terrain> map = Map.of(new Position(1, 0), new Mountain(),
				new Position(2, 1), new Mountain(),
				new Position(0, 3), new Treasure(2),
				new Position(1, 3), new Treasure(3),
				new Position(1, 1), new Adventurer("Lara", Orientation.SOUTH, listOfActions, 0, 0));
		Simulation simulation = new Simulation(3, 4, map);
		
		Map<Position, Terrain> finalState = simulation.getFinalState();
		
		Adventurer expectedAdventurer = new Adventurer("Lara", Orientation.SOUTH, Lists.list(), 3, 0);
		Map<Position, Terrain> expectedFinalState = Map.of(new Position(1, 0), new Mountain(),
				new Position(2, 1), new Mountain(),
				new Position(0, 3), expectedAdventurer,
				new Position(1, 3), new Treasure(2));
		
		assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
	}

	@Nested
	class OutOfBoundsMoves {

		@Test
		void an_adventurer_cannot_advance_on_an_out_of_bounds_tile_trying_on_the_right() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new MountainLineToken(1, 0),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.WEST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Mountain());
			expectedFinalState.put(new Position(0, 0), new Adventurer("Adventurer", Orientation.WEST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_cannot_advance_on_an_out_of_bounds_tile_trying_on_the_top() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new MountainLineToken(1, 0),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.NORTH, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Mountain());
			expectedFinalState.put(new Position(0, 0), new Adventurer("Adventurer", Orientation.NORTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_cannot_advance_on_an_out_of_bounds_tile_trying_on_the_left() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new MountainLineToken(1, 0),
					new AdventurerLineToken("Adventurer", 9, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Mountain());
			expectedFinalState.put(new Position(9, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_cannot_advance_on_an_out_of_bounds_tile_trying_on_the_bottom() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new MountainLineToken(1, 0),
					new AdventurerLineToken("Adventurer", 0, 9, Orientation.SOUTH, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Mountain());
			expectedFinalState.put(new Position(0, 9), new Adventurer("Adventurer", Orientation.SOUTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
	}
	
	@Nested
	class AdventurerOrientation {
		
		@Test
		void an_adventurer_oriented_south_should_be_oriented_east_when_turning_left() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.SOUTH, Lists.list(AdventurerAction.TURN_LEFT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_south_should_be_oriented_west_when_turning_right() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.SOUTH, Lists.list(AdventurerAction.TURN_RIGHT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.WEST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_west_should_be_oriented_south_when_turning_left() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.WEST, Lists.list(AdventurerAction.TURN_LEFT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.SOUTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_west_should_be_oriented_north_when_turning_right() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.WEST, Lists.list(AdventurerAction.TURN_RIGHT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.NORTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_north_should_be_oriented_west_when_turning_left() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.NORTH, Lists.list(AdventurerAction.TURN_LEFT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.WEST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_north_should_be_oriented_east_when_turning_right() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.NORTH, Lists.list(AdventurerAction.TURN_RIGHT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_east_should_be_oriented_north_when_turning_left() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.EAST, Lists.list(AdventurerAction.TURN_LEFT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.NORTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
		@Test
		void an_adventurer_oriented_east_should_be_oriented_south_when_turning_right() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Adventurer", 2, 0, Orientation.EAST, Lists.list(AdventurerAction.TURN_RIGHT)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.SOUTH, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
	}

	@Nested
	class TreasurePicking {

		@Test
		void an_adventurer_advancing_on_a_treasure_should_get_the_treasure_and_treasure_count_should_be_decremented() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new TreasureLineToken(1, 0, 2),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE, AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Treasure(1));
			expectedFinalState.put(new Position(2, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 1, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_advancing_on_a_treasure_should_get_the_treasure_and_treasure_disappears_when_the_last_one_has_been_picked() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new TreasureLineToken(1, 0, 1),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE, AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(2, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 1, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_finishing_on_a_treasure_non_empty_should_be_represented_by_a_treasure_tile_with_an_adventurer() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new TreasureLineToken(1, 0, 2),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Treasure expectedTreasureTile = new Treasure(1);
			Adventurer adventurer = new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 1, 0);
			expectedTreasureTile.withAdventurer(adventurer);
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), expectedTreasureTile);
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_finishing_on_an_empty_treasure_should_be_represented_an_adventurer() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new TreasureLineToken(1, 0, 1),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Adventurer adventurer = new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 1, 0);
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), adventurer);
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
	}
	
	@Nested
	class BlockedMoves {

		@Test
		void an_adventurer_cannot_advance_on_a_mountain_terrain() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new MountainLineToken(1, 0),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Mountain());
			expectedFinalState.put(new Position(0, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 0));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_cannot_advance_on_another_adventurer() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("Not moving Adventurer", 1, 0, Orientation.EAST, Lists.list()),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Adventurer("Not moving Adventurer", Orientation.EAST, Lists.list(), 0, 0));
			expectedFinalState.put(new Position(0, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 1));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}

		@Test
		void an_adventurer_can_block_another_one_if_they_advance_first() throws Exception {
			
			List<LineToken> tokens = Lists.list(new MapLineToken(10, 10),
					new AdventurerLineToken("blocking Adventurer", 2, 0, Orientation.WEST, Lists.list(AdventurerAction.ADVANCE)),
					new AdventurerLineToken("Adventurer", 0, 0, Orientation.EAST, Lists.list(AdventurerAction.ADVANCE)));
			
			Simulation simulation = new Parser(tokens).getSimulation();
			Map<Position, Terrain> finalState = simulation.getFinalState();
			
			Map<Position, Terrain> expectedFinalState = Maps.newHashMap(new Position(1, 0), new Adventurer("blocking Adventurer", Orientation.WEST, Lists.list(), 0, 0));
			expectedFinalState.put(new Position(0, 0), new Adventurer("Adventurer", Orientation.EAST, Lists.list(), 0, 1));
			
			assertThat(finalState).containsExactlyInAnyOrderEntriesOf(expectedFinalState);
		}
		
	}
	

}
