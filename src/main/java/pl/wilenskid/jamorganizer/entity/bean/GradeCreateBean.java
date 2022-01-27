package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GradeCreateBean {
	private Byte grade;
    private Long submissionId;
    private Long criterionId;
}
