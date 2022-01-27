package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class FilesGroupAddBean {
    private Long filesGroupId;
    private MultipartFile file;
}
