package pl.wilenskid.jamorganizer.bean;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;

@Getter
@Builder
public class MemberBean {
	private Long id;
	private ApplicationUserEventRole applicationUserEventRole;
    private Long event;
    private Long applicationUser;
}
