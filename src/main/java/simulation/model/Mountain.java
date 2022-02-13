package simulation.model;

public class Mountain implements Terrain {
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isBlocked() {
		return true;
	}

}
