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
		default:
			throw new LineFormatException(toLex,
					"The map line format should start with a 'C' and have three components separated by dashes");
		}
	}
}
