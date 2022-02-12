public class Lexer {
	
	public static MapLineToken toLineToken(String toLex) {
		String[] splitted = toLex.split("\\s*-\\s*");
		if(splitted.length == 3 && splitted[0].equals("C")) {
			return new MapLineToken(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
		}
		throw new LineFormatException(toLex, "The map line format should start with a 'C' and have three components separated by dashes");
	}
}
