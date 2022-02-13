import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MainTest {

	private static final String SIMPLE_EXAMPLE_FILE = "src/test/resources/simple-example.txt";
	private static final String TEST_RESOURCES_OUTPUT_TXT = "src/test/resources/output.txt";
	private static final String EXPECTED_OUTPUT_TXT = "src/test/resources/expected-output.txt";

	@Test
	void example_and_expected_output_should_be_the_same() throws Exception {
		Main.main(new String[] {SIMPLE_EXAMPLE_FILE});
		
		assertThat(new File(TEST_RESOURCES_OUTPUT_TXT)).hasSameTextualContentAs(new File(EXPECTED_OUTPUT_TXT));
	}
	
	@AfterEach
	void cleanup() {
		new File(TEST_RESOURCES_OUTPUT_TXT).delete();
	}
}
