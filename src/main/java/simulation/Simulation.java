package simulation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import simulation.model.Adventurer;
import simulation.model.AdventurerAction;
import simulation.model.Plain;
import simulation.model.Position;
import simulation.model.Terrain;
import simulation.model.Treasure;

public class Simulation {
	
	private static final Comparator<PositionedAdventurer> POSITIONED_ADVENTURER_ORDER_COMPARATOR = new Comparator<PositionedAdventurer>() {

		@Override
		public int compare(PositionedAdventurer o1, PositionedAdventurer o2) {
			return o1.adventurer.compareTo(o2.adventurer);
		}
	};

	private int horizontalNbOfBoxes;
	private int verticalNbOfBoxes;
	private Map<Position, Terrain> map;

	public Simulation(int horizontalNbOfBoxes, int verticalNbOfBoxes, Map<Position, Terrain> map) {
		this.horizontalNbOfBoxes = horizontalNbOfBoxes;
		this.verticalNbOfBoxes = verticalNbOfBoxes;
		this.map = new HashMap<>();
		this.map.putAll(map);
	}

	public Map<Position, Terrain> getFinalState() {
		Queue<PositionedAdventurer> adventurers = retrieveAdventurersInOrder();
		
		if(adventurers.isEmpty()) {
			return map;
		}
		
		boolean hasActed = false;
		
		PositionedAdventurer positionedAdventurer = adventurers.poll();
		while(positionedAdventurer != null) {
			final PositionedAdventurer currentPositionedAdventurer = positionedAdventurer;
			Adventurer adventurer = positionedAdventurer.adventurer;
			Optional<AdventurerAction> nextAction = adventurer.getNextAction();
			
			if(nextAction.isPresent()) {
				hasActed = true;
			}
			
			nextAction.ifPresent(action -> handleAction(action, currentPositionedAdventurer));
			
			positionedAdventurer = adventurers.poll();
		}
		
		if(hasActed) {
			return getFinalState();
		}
		return this.map;
	}
	
	private void handleAction(AdventurerAction action, PositionedAdventurer positionedAdventurer) {
		switch (action) {
		case TURN_LEFT:
			positionedAdventurer.adventurer.turnLeft();
			break;
		case TURN_RIGHT:
			positionedAdventurer.adventurer.turnRight();
			break;
		case ADVANCE:
			Position newPosition = positionedAdventurer.adventurer.simulateAdvance(positionedAdventurer.position);
			if(isValidPosition(newPosition)) {
				doAdvance(positionedAdventurer, newPosition);
			}
			break;

		default:
			throw new IllegalStateException("Unknown action: " + action);
		}
		
	}

	private void doAdvance(PositionedAdventurer positionedAdventurer, Position newPosition) {
		Terrain potentialTerrain = map.getOrDefault(newPosition, new Plain());
		if(potentialTerrain.isBlocked()) {
			return;
		}
		if(potentialTerrain.isTreasure()) {
			Treasure treasure = (Treasure) potentialTerrain;
			positionedAdventurer.adventurer.increaseTreasures();
			int remainingTreasures = treasure.decrementNumberAndGet();
			if(remainingTreasures == 0) {
				map.put(newPosition, positionedAdventurer.adventurer);
				freeUpLeavingPosition(positionedAdventurer.position);
			} else {
				treasure.withAdventurer(positionedAdventurer.adventurer);
				map.put(newPosition, treasure);
				freeUpLeavingPosition(positionedAdventurer.position);
			}
		} else {
			map.put(newPosition, positionedAdventurer.adventurer);
			freeUpLeavingPosition(positionedAdventurer.position);
		}
	}

	private void freeUpLeavingPosition(Position position) {
		Terrain terrain = map.get(position);
		if(terrain.isTreasure()) {
			Treasure treasure = (Treasure) terrain;
			treasure.adventurerLeaves();
			map.put(position, treasure);
		} else {
			map.remove(position);
		}
	}

	private boolean isValidPosition(Position position) {
		int horizontalPosition = position.getHorizontalPosition();
		int verticalPosition = position.getVerticalPosition();
		return horizontalPosition >= 0 && horizontalPosition < horizontalNbOfBoxes && verticalPosition >= 0 && verticalPosition < verticalNbOfBoxes;
	}

	private Queue<PositionedAdventurer> retrieveAdventurersInOrder() {
		return map.entrySet().stream()
				.filter(entry -> Adventurer.class.isInstance(entry.getValue()) || Treasure.class.isInstance(entry.getValue()) && ((Treasure) entry.getValue()).getAdventurer() != null)
				.map(entry -> buildPositionedAdventurer(entry))
				.sorted(POSITIONED_ADVENTURER_ORDER_COMPARATOR)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	private PositionedAdventurer buildPositionedAdventurer(Entry<Position, Terrain> entry) {
		if(Adventurer.class.isInstance(entry.getValue())) {
			return new PositionedAdventurer(entry.getKey(), (Adventurer) entry.getValue());
		} else {
			return new PositionedAdventurer(entry.getKey(), ((Treasure) entry.getValue()).getAdventurer());
		}
	}

	private class PositionedAdventurer {
		private Position position;
		private Adventurer adventurer;
		
		public PositionedAdventurer(Position position, Adventurer adventurer) {
			this.position = position;
			this.adventurer = adventurer;
		}
	}

}
