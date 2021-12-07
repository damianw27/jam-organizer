package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.ApplicationUserCreateBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class ApplicationUserService implements CrudService<ApplicationUser, ApplicationUserCreateBean, Object, Long> {

    private final ApplicationUserRepository applicationUserRepository;
    private final TimeService timeService;

    @Inject
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  TimeService timeService) {
        this.applicationUserRepository = applicationUserRepository;
        this.timeService = timeService;
    }

    @Override
    public List<ApplicationUser> getAll() {
        return StreamSupport
            .stream(applicationUserRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ApplicationUser> getById(Long id) {
        return applicationUserRepository.findById(id);
    }

    @Override
    public ApplicationUser update(Object o) {
        return null;
    }

    @Override
    public Optional<ApplicationUser> delete(Long id) {
        Optional<ApplicationUser> entityToBeDeleted = applicationUserRepository.findById(id);
        entityToBeDeleted.ifPresent(applicationUserRepository::delete);
        return entityToBeDeleted;
    }

    @Override
    public ApplicationUser create(ApplicationUserCreateBean applicationUserBean) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setName(applicationUserBean.getName());
        applicationUser.setDisplayName(applicationUserBean.getDisplayName());
        applicationUser.setEmail(applicationUserBean.getEmail());
        applicationUser.setPhoneNumber(applicationUserBean.getPhoneNumber());
        applicationUser.setPassword(applicationUserBean.getPassword());
        applicationUser.setApplicationUserRole(ApplicationUserRole.NORMAL);
        timeService.dateFromString(applicationUserBean.getJobStart()).ifPresent(applicationUser::setJobStart);
        applicationUser.setCreated(Calendar.getInstance());
        applicationUser.setSuspendExpiration(null);
        applicationUser.setMembership(new HashSet<>());
        applicationUser.setOrganizations(applicationUserBean.getOrganizations());
        applicationUser.setProjects(new HashSet<>());
        applicationUserRepository.save(applicationUser);
        return applicationUser;
    }

}
