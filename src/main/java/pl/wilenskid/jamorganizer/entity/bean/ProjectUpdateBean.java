package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;

@Data
public class ProjectUpdateBean {
    private Long projectId;
    private String title;
    private String description;
}
