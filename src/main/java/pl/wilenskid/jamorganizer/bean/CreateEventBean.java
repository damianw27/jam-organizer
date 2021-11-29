package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateEventBean extends AbstractOperationBean {
    private String name;
    private String descriptionLink;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
}
