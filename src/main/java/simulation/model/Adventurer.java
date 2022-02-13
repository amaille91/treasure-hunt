package simulation.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class Adventurer implements Terrain, Comparable<Adventurer> {

	private String name;
	private Orientation orientation;
	private Queue<AdventurerAction> actions;
	private int possessedTreasures;
	private final int adventurerOrder;

	public Adventurer(String name, Orientation startingOrientation, List<AdventurerAction> sequenceOfActions, int nbOfTreasures, int adventurerOrder) {
		this.name = name;
		this.orientation = startingOrientation;
		this.actions = new LinkedList<>(sequenceOfActions);
		this.possessedTreasures = nbOfTreasures;
		this.adventurerOrder = adventurerOrder;
	}

	public Optional<AdventurerAction> getNextAction() {
		return Optional.ofNullable(this.actions.poll());
	}

	@Override
	public int hashCode() {
		return Objects.hash(actions, adventurerOrder, name, orientation, possessedTreasures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adventurer other = (Adventurer) obj;
		return Objects.equals(actions, other.actions) && adventurerOrder == other.adventurerOrder
				&& Objects.equals(name, other.name) && orientation == other.orientation
				&& possessedTreasures == other.possessedTreasures;
	}

	@Override
	public int compareTo(Adventurer o) {
		return Integer.compare(adventurerOrder, o.adventurerOrder);
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	public void turnLeft() {
		switch (orientation) {
		case EAST:
			this.orientation = Orientation.NORTH;
			break;
		case NORTH:
			this.orientation = Orientation.WEST;
			break;
		case WEST:
			this.orientation = Orientation.SOUTH;
			break;
		case SOUTH:
			this.orientation = Orientation.EAST;
			break;

		default:
			throw new IllegalStateException("Unknown orientation: " + orientation);
		}
	}
	
	public void turnRight() {
		switch (orientation) {
		case EAST:
			this.orientation = Orientation.SOUTH;
			break;
		case NORTH:
			this.orientation = Orientation.EAST;
			break;
		case WEST:
			this.orientation = Orientation.NORTH;
			break;
		case SOUTH:
			this.orientation = Orientation.WEST;
			break;
			
		default:
			throw new IllegalStateException("Unknown orientation: " + orientation);
		}
	}

	public Position simulateAdvance(Position initialPosition) {
		int horizontalPosition = initialPosition.getHorizontalPosition();
		int verticalPosition = initialPosition.getVerticalPosition();
		switch (orientation) {
		case EAST:
			return new Position(horizontalPosition + 1, verticalPosition);
		case NORTH:
			return new Position(horizontalPosition, verticalPosition - 1);
		case WEST:
			return new Position(horizontalPosition - 1, verticalPosition);
		case SOUTH:
			return new Position(horizontalPosition, verticalPosition + 1);
			
		default:
			throw new IllegalStateException("Unknown orientation: " + orientation);
		}
	}

	public void increaseTreasures() {
		this.possessedTreasures++;
	}

	@Override
	public boolean isBlocked() {
		return true;
	}

}
