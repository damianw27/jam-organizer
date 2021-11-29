package pl.wilenskid.jamorganizer.action;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wilenskid.jamorganizer.bean.AbstractOperationBean;
import pl.wilenskid.jamorganizer.bean.CreateApplicationUserBean;
import pl.wilenskid.jamorganizer.bean.CreateEventBean;
import pl.wilenskid.jamorganizer.service.OperationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/operation")
public class OperationAction {
    private final OperationService operationService;

    @Inject
    public OperationAction(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping
    public HttpStatus createAppUser(CreateApplicationUserBean bean,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        return performOperation(bean, request, response);
    }

    private <Bean extends AbstractOperationBean> HttpStatus performOperation(Bean bean,
                                                                             HttpServletRequest request,
                                                                             HttpServletResponse response) {
        operationService.performOperation(request, response, bean);
        return HttpStatus.ACCEPTED;
    }
}
