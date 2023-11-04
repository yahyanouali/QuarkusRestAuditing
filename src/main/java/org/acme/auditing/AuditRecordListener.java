package org.acme.auditing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.acme.auditing.domain.AuditRecord;

@ApplicationScoped
public class AuditRecordListener {

    public void onAuditRecord(@Observes AuditRecord auditRecord) {
        System.out.println("logging the audit record to ELASTIC : " + auditRecord);
    }
}

