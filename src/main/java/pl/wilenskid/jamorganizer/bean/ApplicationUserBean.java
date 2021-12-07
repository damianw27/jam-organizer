package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

@Getter
@Builder
public class ApplicationUserBean {
	private Long id;
	private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private ApplicationUserRole applicationUserRole;
    private String jobStart;
    private String created;
    private String suspendExpiration;
    private List<Long> membership;
    private List<Long> organizations;
    private List<Long> projects;
}
