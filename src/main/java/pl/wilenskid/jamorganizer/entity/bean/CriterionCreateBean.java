package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CriterionCreateBean {
    private String label;
    private Byte priority;
    private Long eventId;
}
