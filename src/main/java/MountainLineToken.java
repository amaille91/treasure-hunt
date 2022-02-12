
public class MountainLineToken implements LineToken {

	private Position position;

	public MountainLineToken(int horizontalPosition, int verticalPosition) {
		this.position = new Position(horizontalPosition, verticalPosition);
	}

	public Position getPosition() {
		return position;
	}

}
