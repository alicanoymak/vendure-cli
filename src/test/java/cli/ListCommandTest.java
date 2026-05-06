package cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class ListCommandTest {

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
    return new ProductService() {
      @Override
      public List<Product> getProducts() {
        return List.of(new Product("Tablet", 32900), new Product("Monitor", 19900));
      }
    };
  }

  @Test
  void shouldPrintTableFormat() {
    ListCommand cmd = new ListCommand(fakeService());
    new CommandLine(cmd).execute("--format", "table");

    String output = outputStream.toString();
    assertTrue(output.contains("Tablet"));
    assertTrue(output.contains("Monitor"));
    assertTrue(output.contains("Name"));
  }

  @Test
  void shouldPrintJsonFormat() {
    ListCommand cmd = new ListCommand(fakeService());
    new CommandLine(cmd).execute("--format", "json");

    String output = outputStream.toString();
    assertTrue(output.contains("\"name\""));
    assertTrue(output.contains("\"Tablet\""));
    assertTrue(output.contains("\"Monitor\""));
    assertTrue(output.trim().startsWith("["));
    assertTrue(output.trim().endsWith("]"));
  }

  @Test
  void shouldUseTableFormatByDefault() {
    ListCommand cmd = new ListCommand(fakeService());
    new CommandLine(cmd).execute();

    String output = outputStream.toString();
    assertTrue(output.contains("Name"));
    assertTrue(output.contains("Tablet"));
  }
}
