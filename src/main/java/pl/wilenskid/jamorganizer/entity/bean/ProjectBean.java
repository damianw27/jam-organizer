package pl.wilenskid.jamorganizer.entity.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBean {
	private Long id;
	private String title;
    private String descriptionContent;
    private String logoLink;
    private String videoLink;
    private String projectLink;
    private String[] picturesLinks;
    private Integer endorsementCount;
    private List<Long> members;
    private List<Long> submissions;
}
