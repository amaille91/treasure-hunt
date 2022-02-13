package lexing;
import java.util.LinkedList;
import java.util.stream.Collectors;

import lexing.exceptions.LineFormatException;
import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import simulation.model.AdventurerAction;
import simulation.model.Orientation;

public class Lexer {

	public static LineToken toLineToken(String toLex) {
		String[] splitted = toLex.split("\\s*-\\s*");

		switch (splitted[0]) {
		case "C":
			if (splitted.length == 3) {
				return new MapLineToken(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
			}
			throw new LineFormatException(toLex,
					"The map line format should start with a 'C' and have three components separated by dashes");
		case "M":
			if (splitted.length == 3) {
				return new MountainLineToken(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
			}
			throw new LineFormatException(toLex,
					"The mountain line format should start with a 'M' and have three components separated by dashes");
		case "T":
			if (splitted.length == 4) {
				return new TreasureLineToken(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
			}
			throw new LineFormatException(toLex,
					"The treasure line format should start with a 'T' and have four components separated by dashes");
		case "A":
			if (splitted.length == 6) {
				String orientationStr = splitted[4];
				if(orientationStr.length() != 1) {
					throw new LineFormatException(toLex, "The starting orientation of an adventurer must only be one letter");
				}
				return new AdventurerLineToken(
						splitted[1],
						Integer.parseInt(splitted[2]),
						Integer.parseInt(splitted[3]),
						Orientation.parseOrientation(orientationStr.charAt(0)),
						splitted[5].chars().mapToObj(intChar -> AdventurerAction.parseAction((char) intChar)).collect(Collectors.toCollection(LinkedList::new)));
			}
			throw new LineFormatException(toLex,
					"The adventurer line format should start with a 'A' and have six components separated by dashes");
		default:
			throw new LineFormatException(toLex,
					"A line should start with either 'C', 'M', 'T' or 'A'");
		}
	}
}
