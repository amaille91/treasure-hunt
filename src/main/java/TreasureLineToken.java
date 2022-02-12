public class TreasureLineToken implements LineToken {

	private Position position;
	private int number;

	public TreasureLineToken(int horizontalPosition, int verticalPosition, int number) {
		this.position = new Position(horizontalPosition, verticalPosition);
		this.number = number;
	}

	public Position getPosition() {
		return this.position;
	}

	public int getNumber() {
		return this.number;
	}

}
