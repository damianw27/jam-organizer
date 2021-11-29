package pl.wilenskid.jamorganizer.operation;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wilenskid.jamorganizer.bean.AbstractOperationBean;
import pl.wilenskid.jamorganizer.enums.OperationType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Operation<Bean extends AbstractOperationBean> {
    void performOperation(HttpServletRequest request, HttpServletResponse response, Bean bean);
    OperationType getOperationType();

    default void redirectTo(HttpServletResponse response, String path) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String currentUrl = response.encodeRedirectURL(baseUrl + path);
        response.setHeader("Location", currentUrl);
        response.setStatus(302);
    }
}
