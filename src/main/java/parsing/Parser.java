package parsing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.exceptions.AlreadyOccupiedSpaceException;
import parsing.exceptions.IllegalMapSizeException;
import parsing.exceptions.NoMapException;
import parsing.exceptions.OutboundTerrainException;
import parsing.exceptions.TooManyMapsException;
import simulation.Simulation;
import simulation.model.Adventurer;
import simulation.model.Mountain;
import simulation.model.Position;
import simulation.model.Terrain;
import simulation.model.Treasure;

public class Parser {

	private int horizontalNbOfBoxes;
	private int verticalNbOfBoxes;
	
	private Map<Position, Terrain> map = new HashMap<>();
	private int adventurerOrder = 0;

	public Parser(List<LineToken> tokens) {
		MapLineToken mapLineToken = retrieveMapLine(tokens);
		int horizontalNbOfBoxes = mapLineToken.getHorizontalNbOfBoxes();
		int verticalNbOfBoxes = mapLineToken.getVerticalNbOfBoxes();
		if(horizontalNbOfBoxes < 1 || verticalNbOfBoxes < 1) {
			throw new IllegalMapSizeException();
		}
		this.horizontalNbOfBoxes = horizontalNbOfBoxes;
		this.verticalNbOfBoxes = verticalNbOfBoxes;
		
		tokens.stream().filter(Predicate.not(MapLineToken.class::isInstance)).forEach(this::withLine);
	}
	
	public void withLine(LineToken token) {
		if(MountainLineToken.class.isInstance(token)) {
			withMountainLine((MountainLineToken) token);
		}
		if(TreasureLineToken.class.isInstance(token)) {
			withTreasureLine((TreasureLineToken) token);
		}
		if(AdventurerLineToken.class.isInstance(token)) {
			withAdventurerLine((AdventurerLineToken) token);
		}
	}
	
	public Simulation getSimulation() {
		return new Simulation(horizontalNbOfBoxes, verticalNbOfBoxes, map);
	}

	private static MapLineToken retrieveMapLine(List<LineToken> tokens) {
		List<LineToken> mapLines = tokens.stream().filter(MapLineToken.class::isInstance).collect(Collectors.toList());
		if(mapLines.size() < 1) {
			throw new NoMapException();
		}
		
		if(mapLines.size() > 1) {
			throw new TooManyMapsException();
		}
		return (MapLineToken) mapLines.get(0);
	}

	private void withAdventurerLine(AdventurerLineToken token) {
		if(isOutbounds(token.getStartingPosition())) {
			throw new OutboundTerrainException();
		}
		
		Terrain previousTerrain = map.put(token.getStartingPosition(), new Adventurer(token.getName(), token.getStartingOrientation(), token.getSequenceOfAction(), 0, adventurerOrder ++));
		if(previousTerrain != null) {
			throw new AlreadyOccupiedSpaceException();
		}
	}

	private void withTreasureLine(TreasureLineToken token) {
		if(isOutbounds(token.getPosition())) {
			throw new OutboundTerrainException();
		}
		Terrain previousTerrain = map.put(token.getPosition(), new Treasure(token.getNumber()));
		if(previousTerrain != null) {
			throw new AlreadyOccupiedSpaceException();
		}
	}

	private void withMountainLine(MountainLineToken token) {
		if(isOutbounds(token.getPosition())) {
			throw new OutboundTerrainException();
		}
		Terrain previousTerrain = map.put(token.getPosition(), new Mountain());
		if(previousTerrain != null) {
			throw new AlreadyOccupiedSpaceException();
		}
	}

	private boolean isOutbounds(Position position) {
		int horizontalPosition = position.getHorizontalPosition();
		int verticalPosition = position.getVerticalPosition();
		return horizontalPosition < 0 || horizontalPosition >= horizontalNbOfBoxes || verticalPosition < 0 || verticalPosition >= verticalNbOfBoxes;
	}
}
