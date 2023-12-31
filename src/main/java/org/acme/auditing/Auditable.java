package org.acme.auditing;

import jakarta.ws.rs.NameBinding;
import org.acme.auditing.domain.Action;
import org.acme.auditing.domain.EntityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auditable {
    Action action() default Action.READ;
    String status() default "OK";

    EntityType entityType() default EntityType.UNKNOWN_ENTITY;
}

