package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateProjectBean extends AbstractOperationBean {
    private String title;
    private String descriptionLink;
    private String logoLink;
    private String videoLink;
    private String downloadLink;
}

