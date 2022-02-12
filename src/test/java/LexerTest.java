import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class LexerTest {

	@Test
	void parsing_a_map_line_should_result_in_a_mapLine_object() throws Exception {
		String mapLineStr = "C - 15 - 20";

		MapLineToken mapLineToken = Lexer.toLineToken(mapLineStr);

		assertThat(mapLineToken.getHorizontalNbOfBoxes()).isEqualTo(15);
		assertThat(mapLineToken.getVerticalNbOfBoxes()).isEqualTo(20);
	}
	
	@Test
	void the_empty_string_should_result_in_LineFormatException() throws Throwable {
		String emptyString = "";
		assertException(LineFormatException.class, assertLineFormatExceptionWithLine(emptyString),
				() -> Lexer.toLineToken(emptyString));
	}

	@Test
	void lines_not_constituted_of_less_than_3_components_separated_by_dashes_should_result_in_LineFormatException()
			throws Throwable {
		String mapLineStr = "C - 20";
		assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mapLineStr),
				() -> Lexer.toLineToken(mapLineStr));
	}

	@Test
	void lines_not_constituted_of_more_than_3_components_separated_by_dashes_should_result_in_LineFormatException()
			throws Throwable {
		String mapLineStr = "C - 20 - 15 - 12";
		assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mapLineStr),
				() -> Lexer.toLineToken(mapLineStr));
	}

	@Test
	void lines_not_starting_with_C_should_result_in_LineFormatException() throws Throwable {
		String mapLineStr = "anythingOtherThanC - 20 - 15";
		assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mapLineStr),
				() -> Lexer.toLineToken(mapLineStr));
	}

	@Test
	void malformed_numbers_in_map_lines_should_result_in_NumberFormatException_first_number() throws Exception {
		String mapLineStr = "C - 15toto - 20";
		assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(mapLineStr));
	}

	@Test
	void malformed_numbers_in_map_lines_should_result_in_NumberFormatException_second_number() throws Exception {
		String mapLineStr = "C - 15 - 2a0";
		assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(mapLineStr));
	}

	private static <E extends Exception> void assertException(Class<E> exceptionClass, Consumer<E> exceptionAssertions,
			Executable executableToTest) throws Throwable {
		try {
			executableToTest.execute();
			fail("Excpected " + exceptionClass + " to be thrown but it wasn't");
		} catch (Exception e) {
			if (!exceptionClass.isInstance(e)) {
				throw e;
			}
			exceptionAssertions.accept(exceptionClass.cast(e));
		}
	}

	private Consumer<LineFormatException> assertLineFormatExceptionWithLine(String expectedFaultyLine) {
		return lfe -> assertThat(lfe.getMalformedLine()).isEqualTo(expectedFaultyLine);
	}

}
