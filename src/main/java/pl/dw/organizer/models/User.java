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
    private Long organizationId;
}
