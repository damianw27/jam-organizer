package pl.wilenskid.jamorganizer.bean;

import lombok.Data;

@Data
public class EventCreateBean {
    private String name;
    private String descriptionLink;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
}
