package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.AbstractOperationBean;
import pl.wilenskid.jamorganizer.enums.OperationType;
import pl.wilenskid.jamorganizer.operation.Operation;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Named
public class OperationService {
    private final List<Operation> operations;

    @Inject
    public OperationService(List<Operation> operations) {
        this.operations = operations;
    }

    public void performOperation(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AbstractOperationBean operationBean) {
        operations.stream()
            .filter(operation -> operation.getOperationType() == operationBean.getOperationType())
            .findFirst()
            .ifPresent(operation -> operation.performOperation(request, response, operationBean));
    }
}
