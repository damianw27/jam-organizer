package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationBean {
	private Long id;
	private String name;
    private String country;
    private List<Long> applicationUsers;
}
