package pl.wilenskid.jamorganizer.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wilenskid.jamorganizer.entity.bean.UserBean;
import pl.wilenskid.jamorganizer.entity.bean.UserCreateBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
@Transactional
public class UserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final TimeService timeService;
    private final PasswordEncoder passwordEncoder;

    @Inject
    public UserService(ApplicationUserRepository applicationUserRepository,
                       TimeService timeService,
                       PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.timeService = timeService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return StreamSupport
            .stream(applicationUserRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public Optional<User> getById(Long id) {
        return applicationUserRepository.findById(id);
    }

    public Optional<User> getByName(String name) {
        return applicationUserRepository.getByName(name);
    }

    public User update(Object o) {
        return null;
    }

    public Optional<User> delete(Long id) {
        Optional<User> entityToBeDeleted = applicationUserRepository.findById(id);
        entityToBeDeleted.ifPresent(applicationUserRepository::delete);
        return entityToBeDeleted;
    }

    public User create(UserCreateBean applicationUserBean) {
        User user = new User();
        user.setName(applicationUserBean.getName());
        user.setDisplayName(applicationUserBean.getDisplayName());
        user.setEmail(applicationUserBean.getEmail());
        user.setPhoneNumber(applicationUserBean.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(applicationUserBean.getPassword()));

        if (applicationUserRepository.count() == 0) {
            user.setUserRole(UserRole.ADMINISTRATOR);
        } else {
            user.setUserRole(UserRole.NORMAL);
        }

        user.setCreated(Calendar.getInstance());
        user.setSuspendExpiration(null);
        user.setMembership(new HashSet<>());
        user.setProjects(new HashSet<>());

        applicationUserRepository.save(user);

        return user;
    }

    public void setUserRole(Long userId, UserRole userRole) {
        User user = applicationUserRepository
            .findById(userId)
            .orElseThrow(NotFoundRestException::new);

        user.setUserRole(userRole);

        applicationUserRepository.save(user);
    }

    public UserBean toBean(User model) {
        List<Long> membership = model
            .getMembership()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        return UserBean.builder()
            .id(model.getId())
            .name(model.getName())
            .displayName(model.getDisplayName())
            .email(model.getEmail())
            .userRole(model.getUserRole())
            .created(timeService.dateToString(model.getCreated(), false).orElse("n/a"))
            .jobStart(timeService.dateToString(model.getJobStart(), false).orElse("n/a"))
            .membership(membership)
            .build();
    }
}
