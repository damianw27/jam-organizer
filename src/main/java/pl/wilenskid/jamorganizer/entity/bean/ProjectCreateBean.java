package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectCreateBean {
    private String title;
    private String description;
    private MultipartFile logo;
    private MultipartFile video;
    private MultipartFile project;
    private MultipartFile[] pictures;
}

