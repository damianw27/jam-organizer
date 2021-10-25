package pl.dw.meetgamejam.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private Calendar jobStart;
    private Calendar created;
    private Calendar suspendExpiration;
    @ManyToOne
    private OrganizationEntity organization;
}
