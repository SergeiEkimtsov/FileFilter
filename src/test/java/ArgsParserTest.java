import com.ekimtsovss.args.ArgsParser;
import com.ekimtsovss.exception.ValidationException;
import com.ekimtsovss.model.Arguments;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ArgsParserTest {
    private final ArgsParser parser = new ArgsParser();

    @Test
    void parse_shouldThrowException_whenNoArguments() {
        assertThatThrownBy(() -> parser.parse(new String[]{}))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("no arguments");
    }

    @Test
    void parse_shouldAcceptSimpleFile() throws ValidationException {
        Arguments result = parser.parse(new String[]{"data.txt"});

        assertThat(result.getListInputFiles()).containsExactly("data.txt");
        assertThat(result.getModeFileWriting()).isFalse();
        assertThat(result.getModeShortStatistic()).isFalse();
        assertThat(result.getModeFullStatistic()).isFalse();
        assertThat(result.getNameFileResult()).contains("");
        assertThat(result.getPathToResults()).contains("");
    }
    @Test
    void parse_shouldAcceptComplexArguments() throws ValidationException {
        String[] args = {
                "-a",
                "-s",
                "-f",
                "-o", "output",
                "-p", "result",
                "input1.txt", "input2.txt"
        };
        Arguments result = parser.parse(args);

        assertTrue(result.getModeFileWriting());
        assertTrue(result.getModeShortStatistic());
        assertTrue(result.getModeFullStatistic());
        assertThat(result.getPathToResults()).isEqualTo("output");
        assertThat(result.getNameFileResult()).isEqualTo("result");
        assertThat(result.getListInputFiles())
                .containsExactly("input1.txt", "input2.txt");
    }

    // Test validation path '-o'
    @Test
    void parse_shouldThrowException_whenPathEmpty() {
        assertThatThrownBy(() -> parser.parse(new String[]{"-o"}))
                .hasMessageContaining("must not be empty");
    }
    @Test
    void parse_shouldCreateDirectory_whenNotExists(@TempDir Path tempDir)
            throws ValidationException {
        Path newDir = tempDir.resolve("dir");
        Arguments result = parser.parse(new String[]{"-o", newDir.toString(), "file.txt"});

        assertThat(result.getPathToResults()).isEqualTo(newDir.toString());
        assertThat(newDir).exists().isDirectory();
    }

    @ParameterizedTest
    @ValueSource(strings = {"dir","\\dir", "\\dir1\\dir2"})
    void parse_shouldAcceptValidPaths(String validPath) throws ValidationException {
        Arguments result = parser.parse(new String[]{"-o", validPath, "file.txt"});
        assertEquals(validPath, result.getPathToResults());
    }

    @ParameterizedTest
    @ValueSource(strings = {"..dir", "/bin/", "/etc/", "c:\\\\windows", "c:\\\\windows\\system"})
    void parse_shouldRejectInvalidPaths(String invalidName) {
        assertThrows(ValidationException.class,
                () -> parser.parse(new String[]{"-p", invalidName, "file.txt"}));
    }

    // Test validation file name '-p'
    @Test
    void parse_shouldThrowException_whenFileNameEmpty() {
        assertThatThrownBy(() -> parser.parse(new String[]{"-p"}))
                .hasMessageContaining("must not be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"nameFile", "name-File", "nameFile123", "nameFile123-"})
    void parse_shouldAcceptValidFileNames(String validName) throws ValidationException {
        Arguments result = parser.parse(new String[]{"-p", validName, "file.txt"});
        assertEquals(validName, result.getNameFileResult());
    }

    @ParameterizedTest
    @ValueSource(strings = {"..name", "name/", "name*", "1name", "name_", "name;", "name."})
    void parse_shouldRejectInvalidFileNames(String invalidName) {
        assertThrows(ValidationException.class,
                () -> parser.parse(new String[]{"-p", invalidName, "file.txt"}));
    }
    // Test validation arg with no prefix and no .txt
    @Test
    void parse_shouldThrowException_whenArgsWithNoPrefixNoTxt() {
        String argWithNoPrefixNoTxt = "a";
        assertThatThrownBy(() -> parser.parse(new String[]{argWithNoPrefixNoTxt}))
                .hasMessageContaining("Invalid argument: '" + argWithNoPrefixNoTxt+ "'");
    }
    // Test validation with no input files
    @Test
    void parse_shouldThrowException_whenArgsWithNoPrefix() {
        assertThatThrownBy(() -> parser.parse(new String[]{"-a"}))
                .hasMessageContaining("""
                                             Invalid argument: no input file
                                             Please provide at least one .txt file.
                                             """);
    }

}
