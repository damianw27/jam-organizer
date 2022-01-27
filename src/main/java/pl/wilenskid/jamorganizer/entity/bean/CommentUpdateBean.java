package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateBean {
    private Long commentId;
    private String commentContent;
}
