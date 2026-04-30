package cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldAcceptUrlBeforeSubcommand() {
        int exitCode = new CommandLine(new Main())
                .execute("--url", "http://localhost:3000/shop-api", "list");

        assertEquals(0, exitCode);
        assertTrue(outputStream.toString().contains("Tablet"));
    }

    @Test
    void shouldAcceptUrlAfterSubcommand() {
        int exitCode = new CommandLine(new Main())
                .execute("list", "--url", "http://localhost:3000/shop-api");

        assertEquals(0, exitCode);
        assertTrue(outputStream.toString().contains("Tablet"));
    }
}
