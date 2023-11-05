package org.acme.ws.generic;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractService<T extends BaseEntity> {

    protected Set<T> entities = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @PostConstruct
    public abstract void init();

    public T create(T entity) {
        entities.add(entity);
        return entity;
    }

    public Set<T> list() {
        return entities;
    }

    public T get(Long id) {
        return entities.stream().filter(entity -> id.equals(entity.getId())).findFirst().orElse(null);
    }

    public T update(Long id, T entity) {
        T existingEntity = get(id);
        if (existingEntity != null) {
            entities.remove(existingEntity);
            entity.setId(id);
            entities.add(entity);
            return entity;
        }
        return null;
    }

    public void delete(Long id) {
        entities.removeIf(entity -> id.equals(entity.getId()));
    }
}
