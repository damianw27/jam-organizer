package pl.wilenskid.jamorganizer.entity.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionBean {
	private Long id;
    private EventBean event;
    private ProjectBean project;
    private List<Long> grades;
}
