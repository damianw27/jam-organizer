package pl.wilenskid.jamorganizer.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Model, CreateBean, UpdateBean, IdType> {
    List<Model> getAll();

    Optional<Model> getById(IdType id);

    Model create(CreateBean createBean);

    Model update(UpdateBean updateBean);

    Optional<Model> delete(IdType id);
}
