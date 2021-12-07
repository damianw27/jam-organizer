package pl.wilenskid.jamorganizer.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;

public interface CrudRest<CreateBean, UpdateBean> {
    @PostMapping
    String create(CreateBean createBean, HttpServletResponse response) throws HttpStatusCodeException;

    @PutMapping
    String update(UpdateBean updateBean, HttpServletResponse response) throws HttpStatusCodeException;

    @DeleteMapping("/{id}")
    String delete(@PathVariable("id") Long id, HttpServletResponse response) throws HttpStatusCodeException;
}
