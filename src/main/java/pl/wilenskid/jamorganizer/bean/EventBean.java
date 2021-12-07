package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventBean {
	private Long id;
	private String name;
    private String descriptionLink;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
    private List<Long> members;
    private List<Long> criteria;
    private List<Long> submissions;
}
