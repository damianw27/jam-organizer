package pl.wilenskid.jamorganizer.bean;

import lombok.Getter;

@Getter
public class GradeCreateBean {
	private Byte grade;
    private Long judge;
    private Long submission;
    private Long criterion;
}
