package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import pl.wilenskid.jamorganizer.enums.OperationType;

@Data
public abstract class AbstractOperationBean {
    public OperationType operationType;
}
