
public class LineFormatException extends RuntimeException {
	private static final long serialVersionUID = -8011193620850397944L;
	private String malformedLine;

	public LineFormatException(String malformedLine, String message) {
		super(message);
		this.malformedLine = malformedLine;
	}

	public String getMalformedLine() {
		return malformedLine;
	}
}
