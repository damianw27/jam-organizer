package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.ProjectCreateBean;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.repository.ProjectRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class ProjectService implements CrudService<Project, ProjectCreateBean, Object, Long> {

    private final ProjectRepository projectRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAll() {
        return StreamSupport
            .stream(projectRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Project> getById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project create(ProjectCreateBean createProjectBean) {
        Project project = new Project();
        project.setTitle(createProjectBean.getTitle());
        project.setDescriptionLink(createProjectBean.getDescriptionLink());
        project.setLogoLink(createProjectBean.getLogoLink());
        project.setVideoLink(createProjectBean.getVideoLink());
        project.setMembers(new HashSet<>());
        project.setSubmissions(new HashSet<>());
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project update(Object o) {
        return null;
    }

    @Override
    public Optional<Project> delete(Long id) {
        Optional<Project> entity = projectRepository.findById(id);
        entity.ifPresent(projectRepository::delete);
        return entity;
    }

}
