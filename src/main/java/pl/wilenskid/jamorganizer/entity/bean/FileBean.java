package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileBean {
    private Long id;
    private String filename;
    private String fileUrl;
    private UserBean author;
    private Long filesGroupId;
}
