package pl.wilenskid.jamorganizer.service;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class ParameterParseService {

    public int getInt(String variableName, Map<String, String> params) {
        return getInt(variableName, params, 0);
    }

    public int getInt(String variableName, Map<String, String> params, int defaultValue) {
        if (params == null || !params.containsKey(variableName)) {
            return defaultValue;
        }

        String paramValue = params.get(variableName);
        return Integer.parseInt(paramValue);
    }

    public long getLong(String variableName, Map<String, String> params) {
        return getLong(variableName, params, 0L);
    }

    public long getLong(String variableName, Map<String, String> params, long defaultValue) {
        if (params == null || !params.containsKey(variableName)) {
            return defaultValue;
        }

        String paramValue = params.get(variableName);
        return Long.parseLong(paramValue);
    }

    public List<Integer> getValidationErrors(Map<String, String> params) {
        String s = params.get("validationErrors");

        if (s == null) {
            return new ArrayList<>();
        }

        return Arrays
            .stream(s.split(","))
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }

}
