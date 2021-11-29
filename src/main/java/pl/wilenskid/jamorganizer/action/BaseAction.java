package pl.wilenskid.jamorganizer.action;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseAction {
    private final String baseTemplatePath;

    protected BaseAction(String baseTemplatePath) {
        this.baseTemplatePath = baseTemplatePath;
    }

    @RequestMapping
    public String getActionTemplateView(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String> pathParams = getPathParams();
        onLoad(request, response, pathParams);
        putMethodsAndFields(model);

        if (pathParams != null) {
            putPathParams(pathParams, model);
        }

        return baseTemplatePath;
    }

    public abstract void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams);

    public abstract List<ApplicationUserRole> getAllowedRoles();

    private void putMethodsAndFields(Model model) {
        Class<? extends BaseAction> actionClass = this.getClass();

        Arrays.stream(actionClass.getFields())
            .filter(this::isFieldInjectable)
            .forEach(field -> addFieldToModel(field, model));

        Arrays.stream(actionClass.getMethods())
            .filter(this::isMethodInjectable)
            .forEach(method -> addMethodToModel(method, model));
    }

    private boolean isFieldInjectable(Field field) {
        return field.getModifiers() == Modifier.PUBLIC;
    }

    private void addFieldToModel(Field field, Model model) {
        try {
            model.addAttribute(field.getName(), field.get(this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean isMethodInjectable(Method method) {
        return method.getParameterCount() == 0
            && method.getModifiers() == Modifier.PUBLIC
            && method.getName().startsWith("action");
    }

    private void addMethodToModel(Method method, Model model) {
        try {
            String methodName = method.getName().replace("action", "");
            String passedName = "do" + methodName;
            model.addAttribute(passedName, method.invoke(this));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getPathParams() {
        String endpoint = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        String[] endpointParts = endpoint.split("\\?");

        if (endpointParts.length == 1) {
            return null;
        }

        String paramsPart = endpointParts[1];

        return Arrays.stream(paramsPart.split("&"))
            .map(parameterString -> parameterString.split("="))
            .filter(paramData -> paramData.length == 2)
            .map(parameter -> Map.of(parameter[0], parameter[1]))
            .collect(HashMap::new, Map::putAll, Map::putAll);
//            .forEach((paramData) -> {
//                System.out.println(Arrays.toString(paramData));
//                model.addAttribute("_" + paramData[0], paramData[1]);
//            });
    }

    private void putPathParams(Map<String, String> pathParams, Model model) {
        pathParams
            .forEach((key, value) -> model.addAttribute("_" + key, value));
    }
}
