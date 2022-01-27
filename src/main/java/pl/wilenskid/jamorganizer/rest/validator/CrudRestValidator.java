package pl.wilenskid.jamorganizer.rest.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudRestValidator<CreateBean, UpdateBean> {
    public List<Integer> validateCreateBean(CreateBean createBean) {
        return Arrays
            .stream(this.getClass().getMethods())
            .filter(method -> method.getName().startsWith("createStep"))
            .filter(method -> method.getReturnType().equals(Integer.class))
            .filter(method -> method.getParameterCount() == 1)
            .filter(method -> method.getParameterTypes()[0] == createBean.getClass())
            .map(method -> {
                try {
                    return (Integer) method.invoke(createBean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalStateException();
                }
            })
            .collect(Collectors.toList());
    }

    public List<Integer> validateUpdateBean(UpdateBean updateBean) {
        return Arrays
            .stream(this.getClass().getMethods())
            .filter(method -> method.getName().startsWith("updateStep"))
            .filter(method -> method.getReturnType().equals(Integer.class))
            .filter(method -> method.getParameterCount() == 1)
            .filter(method -> method.getParameterTypes()[0] == updateBean.getClass())
            .map(method -> {
                try {
                    return (Integer) method.invoke(updateBean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalStateException();
                }
            })
            .collect(Collectors.toList());
    }
}
