package pl.wilenskid.jamorganizer.action.generic;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonBean;
import pl.wilenskid.jamorganizer.entity.bean.UserBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.ReportReasonService;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/admin/panel")
public class AdminPanelAction extends BaseAction {

    private final ReportReasonService reportReasonService;
    private final UserService userService;

    public List<ReportReasonBean> reportReasons;
    public List<UserBean> moderators;
    public List<UserBean> users;

    @Named
    private AdminPanelAction(ReportReasonService reportReasonService, UserService userService) {
        super("generic/admin-panel");
        this.reportReasonService = reportReasonService;
        this.userService = userService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        reportReasons = reportReasonService
            .getAllReportReasons()
            .map(reportReasonService::toBean)
            .collect(Collectors.toList());

        moderators = userService
            .getAll()
            .stream()
            .filter(user -> user.getUserRole() == UserRole.MODERATOR)
            .map(userService::toBean)
            .collect(Collectors.toList());

        users = userService
            .getAll()
            .stream()
            .map(userService::toBean)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return List.of(UserRole.ADMINISTRATOR);
    }
}
