package printing;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import simulation.model.Adventurer;
import simulation.model.Mountain;
import simulation.model.Orientation;
import simulation.model.Position;
import simulation.model.Terrain;
import simulation.model.Treasure;

public class Printer {

	private static final Comparator<Entry<Position, Terrain>> POSITION_COMPARATOR = new Comparator<Map.Entry<Position, Terrain>>() {

		@Override
		public int compare(Entry<Position, Terrain> o1, Entry<Position, Terrain> o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
	};

	public static List<String> toStrings(int nbOfHorizontalBoxes, int nbOfVerticalBoxes,
			Map<Position, Terrain> finalState) {
		final LinkedList<String> result = new LinkedList<>();
		result.add(String.format("C - %d - %d", nbOfHorizontalBoxes, nbOfVerticalBoxes));

		finalState.entrySet().stream()
		    .filter(entry -> Mountain.class.isInstance(entry.getValue()))
		    .sorted(POSITION_COMPARATOR)
		    .forEach(entry -> result.add(String.format("M - %d - %d", entry.getKey().getHorizontalPosition(), entry.getKey().getVerticalPosition())));
		
		finalState.entrySet().stream()
		    .filter(entry -> Treasure.class.isInstance(entry.getValue()))
		    .sorted(POSITION_COMPARATOR)
		    .forEach(entry -> result.add(String.format("T - %d - %d - %d",
		    				entry.getKey().getHorizontalPosition(),
		    				entry.getKey().getVerticalPosition(),
		    				((Treasure) entry.getValue()).getNumber())));
		
		finalState.entrySet().stream()
	    .filter(entry -> Adventurer.class.isInstance(entry.getValue()) || Treasure.class.isInstance(entry.getValue()) && ((Treasure) entry.getValue()).getAdventurer() != null)
	    .sorted(POSITION_COMPARATOR)
	    .forEach(entry -> {
	    	Adventurer adventurer = getAdventurerFromTerrain(entry.getValue());
	    	result.add(String.format("A - %s - %d - %d - %c - %d",
	    			adventurer.getName(),
	    			entry.getKey().getHorizontalPosition(),
	    			entry.getKey().getVerticalPosition(),
	    			orientationToString(adventurer.getOrientation()),
	    			adventurer.getNumberOfPossessedTreasures()
	    			));
	    });

		return result;
	}

	private static Adventurer getAdventurerFromTerrain(Terrain terrain) {
		if(Adventurer.class.isInstance(terrain)) {
			return (Adventurer) terrain;
		}
		if(Treasure.class.isInstance(terrain)) {
			Treasure treasure = (Treasure) terrain;
			return treasure.getAdventurer();
		}
		throw new IllegalStateException("Cannot retrieve adventurer from a " + terrain.getClass().getSimpleName());
	}

	private static char orientationToString(Orientation orientation) {
		switch (orientation) {
		case EAST:
			return 'E';
		case NORTH:
			return 'N';
		case SOUTH:
			return 'S';
		case WEST:
			return 'W';
		default:
			throw new IllegalStateException("Unknown orientation: " + orientation);
		}
	}
	
	

}
