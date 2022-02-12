public class ActionFormatException extends RuntimeException {
	private static final long serialVersionUID = -780052625325004274L;
	private char malformedAction;

	public ActionFormatException(char malformedAction, String string) {
		this.malformedAction = malformedAction;
	}

	public char getMalformedAction() {
		return malformedAction;
	}

}
