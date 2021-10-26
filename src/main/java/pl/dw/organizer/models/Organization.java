package pl.dw.organizer.models;

import lombok.Data;
import pl.dw.organizer.entities.OrganizationEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Organization {
    private Long id;
    private String name;
    private String country;
    private List<User> users;

    public static Organization of(OrganizationEntity organizationEntity) {
        Organization organization = new Organization();
        organization.setId(organizationEntity.getId());
        organization.setName(organizationEntity.getName());
        organization.setCountry(organizationEntity.getCountry());

//        List<User> users = organizationEntity
//            .getUsers()
//            .stream()
//            .map(userEntity -> User.of(userEntity, organization))
//            .collect(Collectors.toList());

//        organization.setUsers(users);
        return organization;
    }
}
