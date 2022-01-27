package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilesGroupRemoveBean {
    private Long filesGroupId;
    private Long fileId;
}
