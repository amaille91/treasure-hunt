package lexing.model;
import java.util.List;

import simulation.model.AdventurerAction;
import simulation.model.Orientation;
import simulation.model.Position;

public class AdventurerLineToken implements LineToken {

	private String name;
	private Position startingPosition;
	private Orientation startingOrientation;
	private List<AdventurerAction> adventurerActions;

	public AdventurerLineToken(String name, int horizontalPosition, int verticalPosition, Orientation startingOrientation,
			List<AdventurerAction> adventurerActions) {
		this.name = name;
		this.startingPosition = new Position(horizontalPosition, verticalPosition);
		this.startingOrientation = startingOrientation;
		this.adventurerActions = adventurerActions;
	}

	public Position getStartingPosition() {
		return startingPosition;
	}

	public Orientation getStartingOrientation() {
		return startingOrientation;
	}

	public String getName() {
		return name;
	}

	public List<AdventurerAction> getSequenceOfAction() {
		return adventurerActions;
	}

}
