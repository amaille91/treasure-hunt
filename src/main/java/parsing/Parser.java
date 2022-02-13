package parsing;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import lexing.model.LineToken;
import lexing.model.MapLineToken;
import parsing.exceptions.NoMapException;
import simulation.model.Simulation;

public class Parser {

	public static Simulation parse(Queue<LineToken> tokens) {
		MapLineToken mapLine = retrieveMapLine(tokens);
		throw new UnsupportedOperationException("not yet implemented");
	}

	private static MapLineToken retrieveMapLine(Queue<LineToken> tokens) {
		List<LineToken> mapLines = tokens.stream().filter(MapLineToken.class::isInstance).collect(Collectors.toList());
		if(mapLines.size() < 1) {
			throw new NoMapException();
		}
		throw new UnsupportedOperationException("not yet implemented");
	}

}
