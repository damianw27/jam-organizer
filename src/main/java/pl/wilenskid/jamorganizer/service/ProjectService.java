package pl.wilenskid.jamorganizer.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.bean.ProjectCreateBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.entity.File;
import pl.wilenskid.jamorganizer.entity.FilesGroup;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.bean.ProjectUpdateBean;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.ProjectRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final FileService fileService;
    private final SecurityService securityService;

    @Inject
    public ProjectService(ProjectRepository projectRepository,
                          UserService userService,
                          FileService fileService,
                          SecurityService securityService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.securityService = securityService;
    }

    public List<Project> getAll() {
        return StreamSupport
            .stream(projectRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public Optional<Project> getById(Long id) {
        return projectRepository.findById(id);
    }

    public Project create(ProjectCreateBean createProjectBean) {
        String fileBaseName = createProjectBean.getTitle().toLowerCase(Locale.ROOT).replace(' ', '-');
        String descFilename = fileBaseName + "-desc";
        String logoFilename = fileBaseName + "-logo";
        String videoFilename = fileBaseName + "-video";
        String projectFilename = fileBaseName + "-project";
        String picturesBaseFilename = fileBaseName + "-pictures";

        FilesGroup picturesFileGroup = fileService.uploadFiles(picturesBaseFilename, createProjectBean.getPictures());

        Set<User> members = Set.of(securityService.getLoggedInUser());

        Project project = new Project();
        project.setTitle(createProjectBean.getTitle());

        fileService.uploadFile(descFilename, createProjectBean.getDescription())
            .ifPresent(file -> project.setDescriptionFileId(file.getId()));

        fileService.uploadFile(logoFilename, createProjectBean.getLogo())
            .ifPresent(file -> project.setLogoFileId(file.getId()));

        fileService.uploadFile(videoFilename, createProjectBean.getVideo())
            .ifPresent(file -> project.setVideoFileId(file.getId()));

        fileService.uploadFile(projectFilename, createProjectBean.getProject())
            .ifPresent(file -> project.setProjectFileId(file.getId()));

        project.setPicturesFilesGroupId(picturesFileGroup.getId());
        project.setMembers(members);
        project.setSubmissions(new HashSet<>());

        projectRepository.save(project);

        return project;
    }

    public Project update(ProjectUpdateBean projectUpdateBean) {
        Project project = projectRepository
            .findById(projectUpdateBean.getProjectId())
            .orElseThrow(NotFoundRestException::new);

        project.setTitle(projectUpdateBean.getTitle());
        fileService.updateFileContent(project.getDescriptionFileId(), projectUpdateBean.getDescription());

        projectRepository.save(project);

        return project;
    }

    public Optional<Project> delete(Long id) {
        Optional<Project> entity = projectRepository.findById(id);
        entity.ifPresent(projectRepository::delete);
        return entity;
    }

    public void addAuthorToProject(Long projectId, Long authorId) {
        User appUser = userService.getById(authorId).orElseThrow(NotFoundRestException::new);
        Project project = projectRepository.findById(projectId).orElseThrow(NotFoundRestException::new);
        project.getMembers().add(appUser);
        projectRepository.save(project);
    }

    public void removeAuthorFromProject(Long projectId, Long authorId) {
        Project project = projectRepository.findById(projectId).orElseThrow(NotFoundRestException::new);

        Set<User> updatedAuthors = project
            .getMembers()
            .stream()
            .filter(applicationUser -> !Objects.equals(applicationUser.getId(), authorId))
            .collect(Collectors.toSet());

        project.setMembers(updatedAuthors);
        projectRepository.save(project);
    }

    public ProjectBean toBean(Project model) {
        List<Long> members = model
            .getMembers()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        List<Long> submissions = model
            .getSubmissions()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        String descriptionContent = fileService
            .findFileById(model.getDescriptionFileId())
            .map(File::getFilename)
            .map(fileService::loadFileAsString)
            .orElse("");

        String logoUrl = fileService
            .findFileById(model.getLogoFileId())
            .map(File::getFileUrl)
            .orElse("");

        String videoUrl = fileService
            .findFileById(model.getVideoFileId())
            .map(File::getFileUrl)
            .orElse("");

        String projectUrl = fileService
            .findFileById(model.getProjectFileId())
            .map(File::getFileUrl)
            .orElse("");

        String[] picturesLinks = fileService
            .findFilesWithId(model.getPicturesFilesGroupId())
            .getFiles()
            .stream()
            .map(File::getFileUrl)
            .toArray(String[]::new);

        return ProjectBean
            .builder()
            .id(model.getId())
            .title(model.getTitle())
            .descriptionContent(descriptionContent)
            .logoLink(logoUrl)
            .videoLink(videoUrl)
            .projectLink(projectUrl)
            .picturesLinks(picturesLinks)
            .endorsementCount(model.getEndorsements().size())
            .members(members)
            .submissions(submissions)
            .build();
    }

}
