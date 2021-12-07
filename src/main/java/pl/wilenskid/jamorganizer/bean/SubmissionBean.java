package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionBean {
	private Long id;
    private Long event;
    private Long project;
    private List<Long> grades;
}
