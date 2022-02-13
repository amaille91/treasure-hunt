package printing;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import simulation.model.Mountain;
import simulation.model.Position;
import simulation.model.Terrain;

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

		return result;
	}

}
