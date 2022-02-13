package lexing.model;

public class MapLineToken implements LineToken {

	private int horizontalNbOfBoxes;
	private int verticalNbOfBoxes;

	public MapLineToken(int horizontalNbOfBoxes, int verticalNbOfBoxes) {
		this.horizontalNbOfBoxes = horizontalNbOfBoxes;
		this.verticalNbOfBoxes = verticalNbOfBoxes;
	}

	public int getHorizontalNbOfBoxes() {
		return this.horizontalNbOfBoxes;
	}

	public int getVerticalNbOfBoxes() {
		return this.verticalNbOfBoxes;
	}

}
