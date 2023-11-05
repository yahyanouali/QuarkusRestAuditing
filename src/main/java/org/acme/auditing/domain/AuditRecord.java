package org.acme.auditing.domain;


import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RequestScoped
@Getter
@ToString
@NoArgsConstructor
public class AuditRecord {
    private String user;
    private String id;
    private Action action;
    private EntityType entityType;
    private String value1;
    private String value2;
    private String status;
    private String message;

    public boolean isComplete() {
        return entityType != EntityType.UNKNOWN_ENTITY &&
                action != null &&
                user != null &&
                status != null;
    }

    public void addUser(String user) {
        this.user = user;
    }

    public void addId(String id) {
        this.id = id;
    }

    public void addAction(Action action) {
        this.action = action;
    }

    public void addEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void addValue1(String value1) {
        this.value1 = value1;
    }

    public void addValue2(String value2) {
        this.value2 = value2;
    }

    public void addStatus(String status) {
        this.status = status;
    }

    public void addMessage(String message) {
        this.message = message;
    }
}
