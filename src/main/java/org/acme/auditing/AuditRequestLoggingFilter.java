package org.acme.auditing;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import lombok.NoArgsConstructor;
import org.acme.auditing.domain.Action;
import org.acme.auditing.domain.AuditRecord;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Auditable
@NoArgsConstructor
public class AuditRequestLoggingFilter implements ContainerRequestFilter {

    @Inject
    private AuditRecordProducer auditRecordProducer;

    @Context
    ResourceInfo resourceInfo;


    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        AuditRecord auditRecord = auditRecordProducer.produceAuditRecord();

        auditRecord.setUser("USER_TO_GET_FROM_HEADERS");

        Method resourceMethod = resourceInfo.getResourceMethod();
        if (resourceMethod != null) {
            Auditable auditable = resourceMethod.getAnnotation(Auditable.class);
            if( auditable != null) {
                Action action = auditable.action();
                auditRecord.setAction(action);

                // ADD LOGIC HERE TO GET ALL COMMON PARAMS AND POPULATE THE AUDIT RECORD
            }
        }

    }



}
