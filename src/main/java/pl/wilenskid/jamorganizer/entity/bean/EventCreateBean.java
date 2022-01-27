package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventCreateBean {
    private String name;
    private String description;
    private MultipartFile logo;
    private String submissionsStart;
    private String submissionsEnd;
    private String judgementsStart;
    private String judgementsEnd;
    private String resultsDate;
}
