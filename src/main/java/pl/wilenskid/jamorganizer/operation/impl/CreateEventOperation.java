package pl.wilenskid.jamorganizer.operation.impl;

import pl.wilenskid.jamorganizer.bean.CreateEventBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.enums.OperationType;
import pl.wilenskid.jamorganizer.operation.Operation;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class CreateEventOperation implements Operation<CreateEventBean> {
    public final EventService eventService;

    @Inject
    public CreateEventOperation(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void performOperation(HttpServletRequest request, HttpServletResponse response, CreateEventBean bean) {
        Event event = eventService.createEvent(bean);
        redirectTo(response, "/event/view?eventId=" + event.getId());
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.CREATE_EVENT;
    }
}
