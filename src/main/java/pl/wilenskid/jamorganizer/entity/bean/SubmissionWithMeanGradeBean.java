package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Data;
import pl.wilenskid.jamorganizer.entity.Submission;

@Data
@Builder
public class SubmissionWithMeanGradeBean {
    private Submission submission;
    private double meanGrade;
}
