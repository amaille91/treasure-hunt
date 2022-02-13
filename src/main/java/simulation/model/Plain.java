package simulation.model;

public class Plain implements Terrain {

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isBlocked() {
		return false;
	}

}
