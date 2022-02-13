import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lexing.Lexer;
import lexing.model.LineToken;

public class Main {
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalStateException("Treasure hunt should be started with a command-line argument specifying the location of the input file");
		}
		
		List<String> fileLines = Files.readAllLines(Path.of(args[0]));
		
		LinkedList<LineToken> tokens = fileLines.stream().map(Lexer::toLineToken).collect(Collectors.toCollection(LinkedList::new));
	}

}
