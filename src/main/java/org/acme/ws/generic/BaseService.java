package org.acme.ws.generic;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public interface BaseService<T> {
    T create(T entity);
    Set<T> list();
    T get(Long id);
    T update(Long id, T entity);
    void delete(Long id);
}
