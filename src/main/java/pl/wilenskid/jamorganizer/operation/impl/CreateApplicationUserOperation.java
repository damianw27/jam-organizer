package pl.wilenskid.jamorganizer.operation.impl;

import pl.wilenskid.jamorganizer.enums.OperationType;
import pl.wilenskid.jamorganizer.operation.Operation;
import pl.wilenskid.jamorganizer.bean.CreateApplicationUserBean;
import pl.wilenskid.jamorganizer.service.ApplicationUserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class CreateApplicationUserOperation implements Operation<CreateApplicationUserBean> {
    private final ApplicationUserService applicationUserService;

    @Inject
    public CreateApplicationUserOperation(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public void performOperation(HttpServletRequest request,
                                 HttpServletResponse response,
                                 CreateApplicationUserBean bean) {
        applicationUserService.createApplicationUser(bean);
        redirectTo(response, "/login");
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.CREATE_APPLICATION_USER;
    }
}
