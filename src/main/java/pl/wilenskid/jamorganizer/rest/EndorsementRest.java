package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.EndorsementService;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;

@Controller
@RequestMapping("/endorsement")
public class EndorsementRest {

    private final ProjectService projectService;
    private final EndorsementService endorsementService;

    @Inject
    public EndorsementRest(ProjectService projectService,
                           EndorsementService endorsementService) {
        this.projectService = projectService;
        this.endorsementService = endorsementService;
    }

    @GetMapping("/endorse/{projectId}")
    public String endorseProject(@PathVariable long projectId) {
        Project project = projectService
            .getById(projectId)
            .orElseThrow(NotFoundRestException::new);

        endorsementService.endorse(project);

        return RedirectionPath
            .builder()
            .basePath("/project/view")
            .withParam("projectId", String.valueOf(projectId))
            .build();
    }

    @GetMapping("/un-endorse/{projectId}")
    public String unEndorseProject(@PathVariable long projectId) {
        Project project = projectService
            .getById(projectId)
            .orElseThrow(NotFoundRestException::new);

        endorsementService.unEndorse(project);

        return RedirectionPath
            .builder()
            .basePath("/project/view")
            .withParam("projectId", String.valueOf(projectId))
            .build();
    }

}
