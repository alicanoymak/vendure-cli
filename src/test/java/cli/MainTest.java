package cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

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

  private ProductService fakeService() {
    return new ProductService(null) {
      @Override
      public List<Product> getProducts() {
        return List.of(new Product("Tablet", 32900), new Product("Monitor", 19900));
      }
    };
  }

  private CommandLine testCommandLine() {
    CommandLine commandLine = new CommandLine(new Main());
    commandLine.addSubcommand("list", new ListCommand(fakeService()));
    return commandLine;
  }

  @Test
  void shouldAcceptUrlBeforeSubcommand() {
    int exitCode = testCommandLine().execute("--url", "http://localhost:3000/shop-api", "list");

    assertEquals(0, exitCode);
    assertTrue(outputStream.toString().contains("Tablet"));
  }

  @Test
  void shouldAcceptUrlAfterSubcommand() {
    int exitCode = testCommandLine().execute("list", "--url", "http://localhost:3000/shop-api");

    assertEquals(0, exitCode);
    assertTrue(outputStream.toString().contains("Tablet"));
  }
}
