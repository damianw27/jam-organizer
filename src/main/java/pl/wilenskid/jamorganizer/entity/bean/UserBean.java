package pl.wilenskid.jamorganizer.entity.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.UserRole;

@Getter
@Builder
public class UserBean {
	private Long id;
	private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private FileWithContentBean avatar;
    private UserRole userRole;
    private String jobStart;
    private String created;
    private String suspendExpiration;
    private List<Long> membership;
    private List<Long> organizations;
    private List<Long> projects;
}
