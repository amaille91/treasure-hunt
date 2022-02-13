package simulation.model;

import java.util.Objects;

public class Position implements Comparable<Position> {

	private int horizontalPosition;
	private int verticalPosition;

	public Position(int horizontalPosition, int verticalPosition) {
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
	}

	public int getHorizontalPosition() {
		return horizontalPosition;
	}

	public int getVerticalPosition() {
		return verticalPosition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(horizontalPosition, verticalPosition);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return horizontalPosition == other.horizontalPosition && verticalPosition == other.verticalPosition;
	}

	@Override
	public int compareTo(Position o) {
		if(this.horizontalPosition < o.horizontalPosition) {
			return -1;
		}
		if(this.horizontalPosition > o.horizontalPosition) {
			return 1;
		}
		return Integer.compare(this.verticalPosition, o.verticalPosition);
	}

}
