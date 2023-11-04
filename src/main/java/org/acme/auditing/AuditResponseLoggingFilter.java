package org.acme.auditing;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.acme.auditing.domain.Action;
import org.acme.auditing.domain.AuditRecord;

import java.io.IOException;

@Provider
@Auditable
public class AuditResponseLoggingFilter implements ContainerResponseFilter {
    @Inject
    AuditRecordProducer auditRecordProducer;
    @Inject
    Event<AuditRecord> auditRecordEvent;

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        AuditRecord auditRecord = auditRecordProducer.produceAuditRecord();
        // Check for completeness before firing the event
        if (auditRecord.isComplete()) {
            System.out.println("Firing Event on " + auditRecord);
            auditRecordEvent.fireAsync(auditRecord);
        } else {
            // Handle the incomplete audit record case, such as logging a warning or error
            System.err.println("Incomplete AuditRecord: " + auditRecord);
        }
    }
}
