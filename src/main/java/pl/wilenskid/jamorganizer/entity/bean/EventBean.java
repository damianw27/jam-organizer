package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EventBean {
    private Long id;
    private String name;
    private String descriptionContent;
    private String logoLink;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
    private List<Long> members;
    private List<Long> criteria;
    private List<Long> submissions;
}
