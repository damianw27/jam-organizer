package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileWithContentBean {
    private Long id;
    private String filename;
    private String fileUrl;
    private String content;
    private UserBean author;
    private Long filesGroupId;
}
