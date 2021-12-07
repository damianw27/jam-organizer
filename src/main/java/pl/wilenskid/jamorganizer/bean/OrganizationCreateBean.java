package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Getter;

@Getter
public class OrganizationCreateBean {
	private String name;
    private String country;
    private List<Long> applicationUsers;
}
