package pl.dw.organizer.assemblers;

import pl.dw.organizer.entities.UserEntity;
import pl.dw.organizer.models.User;
import pl.dw.organizer.repositories.OrganizationRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserAssembler {
    private final OrganizationRepository organizationRepository;

    @Inject
    public UserAssembler(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public User mapToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setDisplayName(userEntity.getDisplayName());
        user.setEmail(userEntity.getEmail());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setJobStart(userEntity.getJobStart());
        user.setCreated(userEntity.getCreated());
        user.setSuspendExpiration(userEntity.getSuspendExpiration());
        user.setOrganizationId(userEntity.getOrganization().getId());
        return user;
    }

    public UserEntity mapToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setDisplayName(user.getDisplayName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setJobStart(user.getJobStart());
        userEntity.setCreated(user.getCreated());
        userEntity.setSuspendExpiration(user.getSuspendExpiration());

        organizationRepository
            .findById(user.getOrganizationId())
            .ifPresent(userEntity::setOrganization);

        return userEntity;
    }
}
