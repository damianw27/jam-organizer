package pl.wilenskid.jamorganizer.action.project;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.ProjectService;
import pl.wilenskid.jamorganizer.service.ReportReasonService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/project/report")
public class ProjectReportAction extends BaseAction {

    private final ProjectService projectService;
    private final ParameterParseService parameterParseService;
    private final ReportReasonService reportReasonService;

    public ProjectBean project;
    public List<ReportReasonBean> reportReasons;

    @Inject
    private ProjectReportAction(ProjectService projectService,
                                ParameterParseService parameterParseService,
                                ReportReasonService reportReasonService) {
        super("project/project-report");
        this.projectService = projectService;
        this.parameterParseService = parameterParseService;
        this.reportReasonService = reportReasonService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long projectId = parameterParseService.getLong("projectId", pathParams, -1);
        Project projectModel = projectService.getById(projectId).orElseThrow(NotFoundRestException::new);
        project = projectService.toBean(projectModel);

        reportReasons = reportReasonService
            .getAllReportReasons()
            .map(reportReasonService::toBean)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
