package cli.graphql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GraphQlClient {

  private final URI endpoint;
  private final HttpClient httpClient;
  private final ObjectMapper mapper;

  public GraphQlClient(String endpoint) {
    this(URI.create(endpoint), HttpClient.newHttpClient(), new ObjectMapper());
  }

  public GraphQlClient(URI endpoint, HttpClient httpClient, ObjectMapper mapper) {
    this.endpoint = endpoint;
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  public <T> T execute(GraphQlQuery<T> query) {
    try {
      String requestBody = mapper.createObjectNode().put("query", query.toGraphQl()).toString();

      HttpRequest request =
          HttpRequest.newBuilder(endpoint)
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(requestBody))
              .build();

      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() < 200 || response.statusCode() >= 300) {
        throw new GraphQlException("GraphQL request failed with status " + response.statusCode());
      }

      JsonNode root = mapper.readTree(response.body());

      if (root.hasNonNull("errors")) {
        throw new GraphQlException("GraphQL response contains errors: " + root.path("errors"));
      }

      return query.parse(root.path("data"));
    } catch (IOException e) {
      throw new GraphQlException("Could not execute GraphQL request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GraphQlException("GraphQL request was interrupted", e);
    }
  }
}
