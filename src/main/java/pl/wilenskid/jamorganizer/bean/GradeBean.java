package pl.wilenskid.jamorganizer.bean;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GradeBean {
	private Long id;
	private Byte grade;
    private Long judge;
    private Long submission;
    private Long criterion;
}
