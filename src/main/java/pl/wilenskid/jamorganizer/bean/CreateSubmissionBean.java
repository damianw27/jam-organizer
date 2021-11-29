package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateSubmissionBean extends AbstractOperationBean {
    private Integer eventId;
    private Integer projectId;
}
