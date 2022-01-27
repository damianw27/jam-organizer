package pl.wilenskid.jamorganizer.builder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedirectionPath {
    private final Map<String, String> params;

    private String basePath;

    private RedirectionPath() {
        this.params = new HashMap<>();
    }

    public RedirectionPath basePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public RedirectionPath withParam(String paramName, String value) {
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        this.params.put(paramName, encodedValue);
        return this;
    }

    public RedirectionPath withParams(String paramName, List<Integer> values) {
        String joinedValues = values
            .stream()
            .map(Object::toString)
            .map(s -> URLEncoder.encode(s, StandardCharsets.UTF_8))
            .collect(Collectors.joining(","));

        params.put(paramName, joinedValues);
        return this;
    }

    public String build() {
        String base = "redirect:" + basePath;

        if (params.size() == 0) {
            return base;
        }

        List<String> concatenatedParams = new ArrayList<>();
        params.forEach((key, value) -> concatenatedParams.add(String.format("%s=%s", key, value)));
        String paramsPath = String.join("&", concatenatedParams);
        return String.format("%s?%s", base, paramsPath);
    }

    public static RedirectionPath builder() {
        return new RedirectionPath();
    }
}
