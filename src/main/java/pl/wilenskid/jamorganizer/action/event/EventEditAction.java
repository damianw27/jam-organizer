package pl.wilenskid.jamorganizer.action.event;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.bean.EventBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.ParameterParseService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/event/edit")
public class EventEditAction extends BaseAction {

    private final EventService eventService;
    private final ParameterParseService parameterParseService;

    @Inject
    public EventEditAction(EventService eventService,
                           ParameterParseService parameterParseService) {
        super("event/event-edit");
        this.eventService = eventService;
        this.parameterParseService = parameterParseService;
    }

    public EventBean event;

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long eventId = parameterParseService.getLong("eventId", pathParams, -1);
        Event eventModel = eventService.getById(eventId).orElseThrow(NotFoundRestException::new);
        event = eventService.toBean(eventModel);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
