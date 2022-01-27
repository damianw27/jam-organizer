package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;
import pl.wilenskid.jamorganizer.enums.ReferenceType;

@Data
public class ReportCreateBean {
    private Long referenceId;
    private ReferenceType referenceType;
    private Long reportReasonId;
    private String description;
}
