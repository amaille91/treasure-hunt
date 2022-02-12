import java.util.Queue;

public class AdventurerLineToken implements LineToken {

	private String name;
	private Position startingPosition;
	private Orientation startingOrientation;
	private Queue<AdventurerAction> adventurerActions;

	public AdventurerLineToken(String name, int horizontalPosition, int verticalPosition, Orientation startingOrientation,
			Queue<AdventurerAction> adventurerActions) {
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

	public Queue<AdventurerAction> getSequenceOfAction() {
		return adventurerActions;
	}

}
