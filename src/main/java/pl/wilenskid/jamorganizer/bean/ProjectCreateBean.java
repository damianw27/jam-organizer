package pl.wilenskid.jamorganizer.bean;

import lombok.Data;

@Data
public class ProjectCreateBean {
    private String title;
    private String descriptionLink;
    private String logoLink;
    private String videoLink;
    private String downloadLink;
}

