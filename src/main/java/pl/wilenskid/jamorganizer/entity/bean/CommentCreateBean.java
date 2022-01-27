package pl.wilenskid.jamorganizer.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.wilenskid.jamorganizer.enums.ReferenceType;

@Getter
@AllArgsConstructor
public class CommentCreateBean {
    private Long referenceId;
    private ReferenceType referenceType;
    private String commentContent;
}
