package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.entity.Endorsement;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.repository.EndorsementRepository;
import pl.wilenskid.jamorganizer.repository.ProjectRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class EndorsementService {

    private final EndorsementRepository endorsementRepository;
    private final ProjectRepository projectRepository;
    private final SecurityService securityService;

    @Inject
    public EndorsementService(EndorsementRepository endorsementRepository,
                              ProjectRepository projectRepository,
                              SecurityService securityService) {
        this.endorsementRepository = endorsementRepository;
        this.projectRepository = projectRepository;
        this.securityService = securityService;
    }

    public boolean canLoggedUserEndorseProject(Project project) {
        User loggedUser = securityService.getLoggedInUser();
        return canEndorseProject(project, loggedUser);
    }

    public boolean canEndorseProject(Project project, User user) {
        return endorsementRepository
            .findByProjectAndUser(project, user)
            .isPresent();
    }

    public void endorse(Project project) {
        User loggedUser = securityService.getLoggedInUser();

        Optional<Endorsement> endorsementExisting = endorsementRepository
            .findByProjectAndUser(project, loggedUser);

        if (endorsementExisting.isPresent()) {
            return;
        }

        Endorsement endorsement = new Endorsement();
        endorsement.setProject(project);
        endorsement.setUser(loggedUser);

        endorsementRepository.save(endorsement);

        Set<Endorsement> endorsements = project.getEndorsements();
        endorsements.add(endorsement);
        project.setEndorsements(endorsements);

        projectRepository.save(project);
    }

    public void unEndorse(Project project) {
        User loggedUser = securityService.getLoggedInUser();

        Endorsement endorsement = endorsementRepository
            .findByProjectAndUser(project, loggedUser)
            .orElse(null);

        if (endorsement == null) {
            return;
        }

        Set<Endorsement> endorsements = project
            .getEndorsements()
            .stream()
            .filter(endorsement1 -> !Objects.equals(endorsement1.getId(), endorsement.getId()))
            .collect(Collectors.toSet());

        project.setEndorsements(endorsements);
        projectRepository.save(project);

        endorsementRepository.delete(endorsement);
    }

}
