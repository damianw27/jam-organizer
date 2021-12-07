package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBean {
	private Long id;
	private String title;
    private String descriptionLink;
    private String logoLink;
    private String videoLink;
    private String downloadLink;
    private List<Long> members;
    private List<Long> submissions;
}
