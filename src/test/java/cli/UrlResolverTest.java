package cli;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlResolverTest {

    @Test
    void shouldUseOptionUrlFirst() {
        UrlResolver resolver = new UrlResolver();

        String url = resolver.resolve(
                "http://localhost:3000/shop-api",
                Map.of("URL", "http://example.com")
        );

        assertEquals("http://localhost:3000/shop-api", url);
    }

    @Test
    void shouldUseEnvironmentUrlWhenOptionIsMissing() {
        UrlResolver resolver = new UrlResolver();

        String url = resolver.resolve(null, Map.of("URL", "http://example.com"));

        assertEquals("http://example.com", url);
    }
}
