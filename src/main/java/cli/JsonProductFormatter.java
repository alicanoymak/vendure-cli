package cli;

import java.util.List;

public class JsonProductFormatter implements ProductFormatter {

  @Override
  public String format(List<Product> products) {
    StringBuilder builder = new StringBuilder();
    builder.append("[\n");

    for (int i = 0; i < products.size(); i++) {
      Product p = products.get(i);
      builder
          .append("  {\"name\": \"")
          .append(p.getName())
          .append("\", \"price\": ")
          .append(p.getPrice())
          .append("}");

      if (i < products.size() - 1) {
        builder.append(",");
      }

      builder.append("\n");
    }

    builder.append("]");
    return builder.toString();
  }
}
