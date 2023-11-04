package org.acme.auditing;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import org.acme.auditing.domain.AuditRecord;

@RequestScoped
public class AuditRecordProducer {

    private AuditRecord auditRecord;

    @PostConstruct
    public void init() {
        auditRecord = new AuditRecord();
        // Initialize with default values if necessary
    }

    @Produces
    public AuditRecord produceAuditRecord() {
        return auditRecord;
    }

    public void setAuditRecord(AuditRecord auditRecord) {
        this.auditRecord = auditRecord;
    }
}
