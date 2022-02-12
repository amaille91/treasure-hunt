import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	void parsing_a_map_line_should_result_in_a_mapLine_object() throws Exception {
		String mapLineStr = "C - 15 - 20";
		
		MapLineToken mapLineToken = Lexer.toLineToken(mapLineStr);
		
		Assertions.assertThat(mapLineToken.getHorizontalNbOfBoxes()).isEqualTo(15);
		Assertions.assertThat(mapLineToken.getVerticalNbOfBoxes()).isEqualTo(20);
	}

}
