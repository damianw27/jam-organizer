package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.ReferenceType;

@Getter
@Builder
public class CommentBean {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private String created;
    private FileWithContentBean commentContent;
    private UserBean author;
}
