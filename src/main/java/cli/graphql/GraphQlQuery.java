package cli.graphql;

import com.fasterxml.jackson.databind.JsonNode;

public interface GraphQlQuery<T> {
  String toGraphQl();

  T parse(JsonNode data);
}
