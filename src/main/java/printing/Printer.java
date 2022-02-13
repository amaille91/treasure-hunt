package printing;

import java.util.List;
import java.util.Map;

import simulation.model.Position;
import simulation.model.Terrain;

public class Printer {

	public static List<String> toStrings(int nbOfHorizontalBoxes, int nbOfVerticalBoxes, Map<Position, Terrain> finalState) {
		return List.of("C - " + nbOfHorizontalBoxes + " - " + nbOfVerticalBoxes);
	}

}
