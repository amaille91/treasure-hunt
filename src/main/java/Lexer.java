public class Lexer {
	
	public static MapLineToken toLineToken(String toLex) {
		String[] splitted = toLex.split("\\s*-\\s*");
		if(splitted.length == 3) {
			return new MapLineToken(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
		}
		throw new UnsupportedLineFormatException("The map line format should start with a 'C' and have three components separated by dashes");
	}

}
