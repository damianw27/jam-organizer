package pl.wilenskid.jamorganizer.bean;

import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;

@Getter
public class MemberCreateBean {
	private ApplicationUserEventRole applicationUserEventRole;
    private Long eventId;
    private Long applicationUserId;
}
