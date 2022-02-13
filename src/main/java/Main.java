import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lexing.Lexer;
import lexing.model.LineToken;
import parsing.Parser;
import printing.Printer;
import simulation.Simulation;

public class Main {
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalStateException("Treasure hunt should be started with a command-line argument specifying the location of the input file");
		}
		
		Path inputFilePath = Path.of(args[0]);
		List<String> fileLines = Files.readAllLines(inputFilePath);
		
		LinkedList<LineToken> tokens = fileLines.stream().map(Lexer::toLineToken).collect(Collectors.toCollection(LinkedList::new));
		Simulation simulation = new Parser(tokens).getSimulation();
		
		List<String> output = Printer.toStrings(simulation.getNbHorizontalBoxes(), simulation.getNbVerticalBoxes(), simulation.getFinalState());
		
		Files.write(Path.of(inputFilePath.getParent().toString(), "output.txt"), String.join("\n", output).getBytes());
	}

}
