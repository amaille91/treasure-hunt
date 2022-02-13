package simulation.model;

import java.util.Objects;

public class Treasure implements Terrain {
	
	private int numberOfTreasures;
	private Adventurer adventurer;

	public Treasure(int number) {
		this.numberOfTreasures = number;
	}

	@Override
	public boolean isTreasure() {
		return true;
	}

	public int decrementNumberAndGet() {
		return --numberOfTreasures;
	}

	public void withAdventurer(Adventurer adventurer) {
		this.adventurer = adventurer;
	}

	@Override
	public boolean isBlocked() {
		return adventurer != null;
	}

	public void adventurerLeaves() {
		this.adventurer = null;
	}

	public Adventurer getAdventurer() {
		return adventurer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adventurer, numberOfTreasures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Treasure other = (Treasure) obj;
		return Objects.equals(adventurer, other.adventurer) && numberOfTreasures == other.numberOfTreasures;
	}

}
