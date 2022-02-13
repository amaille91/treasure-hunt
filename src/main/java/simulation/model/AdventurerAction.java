package simulation.model;
import lexing.exceptions.ActionFormatException;

public enum AdventurerAction {
	ADVANCE, TURN_RIGHT, TURN_LEFT;

	public static AdventurerAction parseAction(char intChar) {
		switch (intChar) {
		case 'A':
			return ADVANCE;
		case 'D':
			return TURN_RIGHT;
		case 'G':
			return TURN_LEFT;
		default:
			throw new ActionFormatException(intChar, "An orientation must be either 'N', 'E', 'S', 'W'");
		}
	}
}
