package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;

@Data
public class EventUpdateBean {
    private Long eventId;
    private String name;
    private String description;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
}
