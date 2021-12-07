package pl.wilenskid.jamorganizer.bean;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CriterionBean {
	private Long id;
    private String label;
    private Boolean required;
    private Byte priority;
    private List<Long> events;
    private List<Long> grades;
}
