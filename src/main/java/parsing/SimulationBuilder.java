package parsing;

import java.util.HashMap;
import java.util.Map;

import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import parsing.exceptions.AlreadyOccupiedSpaceException;
import parsing.exceptions.IllegalMapSizeException;
import parsing.exceptions.OutboundTerrainException;
import simulation.model.Adventurer;
import simulation.model.Mountain;
import simulation.model.Position;
import simulation.model.Terrain;
import simulation.model.Treasure;

public class SimulationBuilder {

	private int horizontalNbOfBoxes;
	private int verticalNbOfBoxes;
	
	private Map<Position, Terrain> map = new HashMap<>();

	public SimulationBuilder(MapLineToken mapLineToken) {
		int horizontalNbOfBoxes = mapLineToken.getHorizontalNbOfBoxes();
		int verticalNbOfBoxes = mapLineToken.getVerticalNbOfBoxes();
		if(horizontalNbOfBoxes < 1 || verticalNbOfBoxes < 1) {
			throw new IllegalMapSizeException();
		}
		this.horizontalNbOfBoxes = horizontalNbOfBoxes;
		this.verticalNbOfBoxes = verticalNbOfBoxes;
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

	private void withAdventurerLine(AdventurerLineToken token) {
		if(isOutbounds(token.getStartingPosition())) {
			throw new OutboundTerrainException();
		}
		
		Terrain previousTerrain = map.put(token.getStartingPosition(), new Adventurer(token.getName(), token.getStartingOrientation(), token.getSequenceOfAction(), 0));
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
