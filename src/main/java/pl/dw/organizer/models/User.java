package pl.dw.organizer.models;

import lombok.Data;
import pl.dw.organizer.entities.UserEntity;

import java.util.Calendar;

@Data
public class User {
    private Long id;
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private Calendar jobStart;
    private Calendar created;
    private Calendar suspendExpiration;
    private Organization organization;

    public static User of(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setDisplayName(userEntity.getDisplayName());
        user.setEmail(userEntity.getEmail());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setJobStart(userEntity.getJobStart());
        user.setCreated(userEntity.getCreated());
        user.setSuspendExpiration(userEntity.getSuspendExpiration());
        return user;
    }

    public static User of(UserEntity userEntity, Organization organization) {
        User user = of(userEntity);
        user.setOrganization(organization);
        return user;
    }
}
