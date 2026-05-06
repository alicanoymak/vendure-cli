package cli;

import java.util.Map;

public class UrlResolver {
  public String resolve(String optionUrl, Map<String, String> environment) {
    if (optionUrl != null && !optionUrl.isBlank()) {
      return optionUrl;
    }

    return environment.get("URL");
  }
}
