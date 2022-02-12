
public class OrientationFormatException extends RuntimeException {
	private static final long serialVersionUID = -8942990763378381842L;
	private char malformedOrientation;

	public OrientationFormatException(char string, String message) {
		super(message);
		this.malformedOrientation = string;
	}

	public char getMalformedOrientation() {
		return malformedOrientation;
	}

}
