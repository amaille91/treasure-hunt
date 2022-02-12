
public class OrientationFormatException extends RuntimeException {
	private static final long serialVersionUID = -8942990763378381842L;
	private String malformedOrientation;

	public OrientationFormatException(String malformedOrientation, String message) {
		super(message);
		this.malformedOrientation = malformedOrientation;
	}

	public String getMalformedOrientation() {
		return malformedOrientation;
	}

}
