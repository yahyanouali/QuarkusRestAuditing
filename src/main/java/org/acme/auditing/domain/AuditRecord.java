package org.acme.auditing.domain;


import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.acme.auditing.domain.Action;

@RequestScoped
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuditRecord {
    private String user;
    private Action action;
    private EntityType entityType;
    private String value1;
    private String value2;

    public boolean isComplete() {
        return entityType != null && action != null && user != null && value1 != null && value2 != null;
    }
}
