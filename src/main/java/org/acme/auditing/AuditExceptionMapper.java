package org.acme.auditing;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.auditing.domain.AuditRecord;

@Auditable
@Provider
public class AuditExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    AuditRecordProducer auditRecordProducer;

    @Inject
    Event<AuditRecord> auditRecordEvent;

    @Override
    public Response toResponse(Throwable exception) {
        // Log the exception
        System.err.println("An error occurred: " + exception.getMessage());

        // Populate the AuditRecord with exception details
        AuditRecord auditRecord = auditRecordProducer.produceAuditRecord();
        auditRecord.addStatus("KO");
        auditRecord.addMessage(exception.getMessage());
        // You can add more details about the exception if needed

        // Fire the audit event with the error information
        System.out.println("Firing Error Event on " + auditRecord);
        auditRecordEvent.fireAsync(auditRecord);

        // Create a response to send back to the client
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof WebApplicationException) {
            status = ((WebApplicationException) exception).getResponse().getStatusInfo().toEnum();
        }
        return Response.status(status)
                .entity("An error occurred: " + exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}

