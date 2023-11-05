package org.acme.auditing.domain;

public enum AuditableKeys {
    ID,
    PRICE,
    EMAIL,
    ADDRESS;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }

}
