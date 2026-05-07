package cli.graphql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class GraphQlClientTest {

  private HttpServer server;
  private String receivedBody;

  @AfterEach
  void tearDown() {
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  void shouldSendQueryAndParseResponse() throws Exception {
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext(
        "/shop-api",
        exchange -> {
          receivedBody =
              new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

          String response =
              """
              {
                "data": {
                  "message": "ok"
                }
              }
              """;

          byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(200, responseBytes.length);
          exchange.getResponseBody().write(responseBytes);
          exchange.close();
        });
    server.start();

    URI endpoint = URI.create("http://localhost:" + server.getAddress().getPort() + "/shop-api");
    GraphQlClient client =
        new GraphQlClient(endpoint, HttpClient.newHttpClient(), new ObjectMapper());

    String result = client.execute(new TestQuery());

    assertTrue(receivedBody.contains("\"query\""));
    assertTrue(receivedBody.contains("test"));
    assertEquals("ok", result);
  }

  private static class TestQuery implements GraphQlQuery<String> {

    @Override
    public String toGraphQl() {
      return """
          query {
            test
          }
          """;
    }

    @Override
    public String parse(JsonNode data) {
      return data.path("message").asText();
    }
  }
}
