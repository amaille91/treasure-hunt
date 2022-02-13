package simulation.model;
import lexing.exceptions.OrientationFormatException;

public enum Orientation {
	NORTH, EAST, SOUTH, WEST;

	public static Orientation parseOrientation(char actionToParse) {
		switch (actionToParse) {
		case 'N':
			return NORTH;
		case 'E':
			return EAST;
		case 'S':
			return SOUTH;
		case 'W':
			return WEST;
		default:
			throw new OrientationFormatException(actionToParse, "An orientation must be either 'N', 'E', 'S', 'W'");
		}
	}
}
