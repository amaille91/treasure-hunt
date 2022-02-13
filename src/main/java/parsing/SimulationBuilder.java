package parsing;

import lexing.model.LineToken;
import lexing.model.MapLineToken;
import parsing.exceptions.IllegalMapSizeException;

public class SimulationBuilder {

	private int horizontalNbOfBoxes;
	private int verticalNbOfBoxes;

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
		throw new UnsupportedOperationException("not yet implemented");
	}
}
