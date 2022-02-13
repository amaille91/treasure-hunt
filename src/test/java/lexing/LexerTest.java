package lexing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.function.Consumer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import lexing.exceptions.ActionFormatException;
import lexing.exceptions.LineFormatException;
import lexing.exceptions.OrientationFormatException;
import lexing.model.AdventurerLineToken;
import lexing.model.LineToken;
import lexing.model.MapLineToken;
import lexing.model.MountainLineToken;
import lexing.model.TreasureLineToken;
import simulation.model.AdventurerAction;
import simulation.model.Orientation;
import simulation.model.Position;

public class LexerTest {

	@Test
	void the_empty_string_should_result_in_LineFormatException() throws Throwable {
		String emptyString = "";
		assertException(LineFormatException.class, assertLineFormatExceptionWithLine(emptyString),
				() -> Lexer.toLineToken(emptyString));
	}
	
	@Nested
	class ParsingMapLines {
		
		@Test
		void parsing_a_map_line_should_result_in_a_mapLine_object() throws Exception {
			String mapLineStr = "C - 15 - 20";

			LineToken lineToken = Lexer.toLineToken(mapLineStr);

			assertThat(lineToken).isInstanceOfSatisfying(MapLineToken.class, mapLineToken -> {

				assertThat(mapLineToken.getHorizontalNbOfBoxes()).isEqualTo(15);
				assertThat(mapLineToken.getVerticalNbOfBoxes()).isEqualTo(20);
			});
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
	}
	
	@Nested
	class ParsingMountainsLines {
		
		@Test
		void parsing_a_mountain_line_should_result_in_a_mountainLine_object() throws Exception {
			String mapLineStr = "M - 2 - 22";

			LineToken lineToken = Lexer.toLineToken(mapLineStr);

			assertThat(lineToken).isInstanceOfSatisfying(MountainLineToken.class,
					mapLineToken -> assertThat(mapLineToken.getPosition()).usingRecursiveComparison().isEqualTo(new Position(2, 22)));
		}
		
		@Test
		void lines_not_constituted_of_less_than_3_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String mountainLineStr = "M - 20";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mountainLineStr),
					() -> Lexer.toLineToken(mountainLineStr));
		}
		
		@Test
		void lines_not_constituted_of_more_than_3_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String mountainLineStr = "M - 20 - 15 - 12";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mountainLineStr),
					() -> Lexer.toLineToken(mountainLineStr));
		}
		
		@Test
		void lines_not_starting_with_M_should_result_in_LineFormatException() throws Throwable {
			String mountainLineStr = "anythingOtherThanM - 20 - 15";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(mountainLineStr),
					() -> Lexer.toLineToken(mountainLineStr));
		}
		
		@Test
		void malformed_numbers_in_mountain_lines_should_result_in_NumberFormatException_first_number() throws Exception {
			String mountainLineStr = "M - 15toto - 20";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(mountainLineStr));
		}
		
		@Test
		void malformed_numbers_in_mountain_lines_should_result_in_NumberFormatException_second_number() throws Exception {
			String mountainLineStr = "M - 15 - 2a0";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(mountainLineStr));
		}
	}
	
	@Nested
	class ParsingTreasuresLines {
		
		@Test
		void parsing_a_treasure_line_should_result_in_a_treasureLine_object() throws Exception {
			String mapLineStr = "T  - 12 - 155   - 3";
			
			LineToken lineToken = Lexer.toLineToken(mapLineStr);
			
			assertThat(lineToken).isInstanceOfSatisfying(TreasureLineToken.class,
					treasureLineToken -> {
						assertThat(treasureLineToken.getPosition()).usingRecursiveComparison().isEqualTo(new Position(12, 155));
						assertThat(treasureLineToken.getNumber()).isEqualTo(3);
					});
		}
		
		@Test
		void lines_not_constituted_of_less_than_4_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String treasureLineStr = "T - 20 - 3";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(treasureLineStr),
					() -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void lines_not_constituted_of_more_than_4_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String treasureLineStr = "M - 20 - 15 - 12 - 1";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(treasureLineStr),
					() -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void lines_not_starting_with_T_should_result_in_LineFormatException() throws Throwable {
			String treasureLineStr = "R - 20 - 15 - 1";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(treasureLineStr),
					() -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void malformed_numbers_in_treasure_lines_should_result_in_NumberFormatException_first_number() throws Exception {
			String treasureLineStr = "T - 15toto - 20 - 1";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void malformed_numbers_in_treasure_lines_should_result_in_NumberFormatException_second_number() throws Exception {
			String treasureLineStr = "T - 15 - 2a0 - 1";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void malformed_numbers_in_treasure_lines_should_result_in_NumberFormatException_third_number() throws Exception {
			String treasureLineStr = "T - 15 - 20 - x1";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(treasureLineStr));
		}
	}
	
	@Nested
	class ParsingAdventurersLines {
		
		@Test
		void parsing_a_treasure_line_should_result_in_a_treasureLine_object() throws Exception {
			String mapLineStr = "A  - Indiana - 2 - 25 - N   - AGGDA";
			
			LineToken lineToken = Lexer.toLineToken(mapLineStr);
			
			assertThat(lineToken).isInstanceOfSatisfying(AdventurerLineToken.class,
					adventurerLineToken -> {
						assertThat(adventurerLineToken.getName()).isEqualTo("Indiana");
						assertThat(adventurerLineToken.getStartingPosition()).usingRecursiveComparison().isEqualTo(new Position(2, 25));
						assertThat(adventurerLineToken.getStartingOrientation()).isEqualTo(Orientation.NORTH);
						assertThat(adventurerLineToken.getSequenceOfAction()).containsExactly(
								AdventurerAction.ADVANCE,
								AdventurerAction.TURN_LEFT,
								AdventurerAction.TURN_LEFT,
								AdventurerAction.TURN_RIGHT,
								AdventurerAction.ADVANCE);
					});
		}
		
		@Test
		void lines_not_constituted_of_less_than_6_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String adventurerLineStr = "A - Indiana - 20 - 3 - S";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(adventurerLineStr),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void lines_not_constituted_of_more_than_6_components_separated_by_dashes_should_result_in_LineFormatException()
				throws Throwable {
			String adventurerLineStr = "A - Indiana - 20 - 15 - E - AGADDA - extraArg";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(adventurerLineStr),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void lines_not_starting_with_A_should_result_in_LineFormatException() throws Throwable {
			String adventurerLineStr = "R - 20 - 15 - 1";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(adventurerLineStr),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void names_containing_dashes_should_result_in_LineFormatException() throws Throwable {
			String adventurerLineStr = "A - Jean-Indiana - 20 - 15 - E - AGADDA";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(adventurerLineStr),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void malformed_numbers_in_adventurers_lines_should_result_in_NumberFormatException_first_number() throws Exception {
			String adventurerLineStr = "A - Indiana - ab20 - 15 - E - AGADDA";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void malformed_numbers_in_adventurers_lines_should_result_in_NumberFormatException_second_number() throws Exception {
			String treasureLineStr = "A - Indiana - 20 - 15h - E - AGADDA";
			assertThrows(NumberFormatException.class, () -> Lexer.toLineToken(treasureLineStr));
		}
		
		@Test
		void more_than_one_char_in_orientation_in_adventurers_lines_should_result_in_LineFormatException() throws Throwable {
			String adventurerLineStr = "A - Indiana - 20 - 15 - toto - AGADDA";
			assertException(LineFormatException.class, assertLineFormatExceptionWithLine(adventurerLineStr),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void illegal_orientation_in_adventurers_lines_should_result_in_OrientationFormatException() throws Throwable {
			String adventurerLineStr = "A - Indiana - 20 - 15 - T - AGADDA";
			assertException(OrientationFormatException.class, assertOrientationFormatExceptionWithLine('T'),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
		
		@Test
		void malformed_action_in_adventurers_lines_should_result_in_ActionFormatException() throws Throwable {
			String adventurerLineStr = "A - Indiana - 20 - 15 - S - AGATDDA";
			assertException(ActionFormatException.class, assertActionFormatExceptionWithLine('T'),
					() -> Lexer.toLineToken(adventurerLineStr));
		}
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
	
	private Consumer<OrientationFormatException> assertOrientationFormatExceptionWithLine(char expectedFaultyOrientation) {
		return ofe -> assertThat(ofe.getMalformedOrientation()).isEqualTo(expectedFaultyOrientation);
	}
	
	private Consumer<ActionFormatException> assertActionFormatExceptionWithLine(char expectedFaultyActions) {
		return ofe -> assertThat(ofe.getMalformedAction()).isEqualTo(expectedFaultyActions);
	}

}
