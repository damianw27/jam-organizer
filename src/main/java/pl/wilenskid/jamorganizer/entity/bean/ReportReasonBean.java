package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportReasonBean {
    private Long id;
    private String label;
    private String description;
}
