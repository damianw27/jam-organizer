package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.service.ProjectService;

@Named
public class ProjectAssembly implements Assembly<Project, ProjectBean> {

    private final ProjectService projectService;
    
    @Inject
    public ProjectAssembly(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @Override
    public ProjectBean toBean(Project model) {
        List<Long> members = StreamSupport.stream(model.getMembers().spliterator(), false)
                .map(member -> member.getId())
                .collect(Collectors.toList());
        
        List<Long> submittions = StreamSupport.stream(model.getSubmissions().spliterator(), false)
                .map(submission -> submission.getId())
                .collect(Collectors.toList());
        
        return ProjectBean
                .builder()
                .id(model.getId())
                .title(model.getTitle())
                .descriptionLink(model.getDescriptionLink())
                .logoLink(model.getLogoLink())
                .videoLink(model.getVideoLink())
                .downloadLink(model.getDownloadLink())
                .members(members)
                .submissions(submittions)
                .build();
    }

    @Override
    public Project toModel(ProjectBean bean) {
        return projectService.getById(bean.getId()).orElse(null);
    }

}
