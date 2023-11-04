package org.acme.ws;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.auditing.AuditRecordProducer;
import org.acme.auditing.domain.Action;
import org.acme.auditing.Auditable;
import org.acme.auditing.domain.AuditRecord;
import org.acme.auditing.domain.EntityType;

@Path("")
public class GreetingResource {

    @Inject
    private AuditRecordProducer auditRecordProducer;


    //@Auditable
    @GET
    @Path("/hello/{firstName}")
    public String helloWithPathParam (@PathParam("firstName") String firstName) {
        return "hello " + firstName;
    }

    @Auditable
    @GET
    @Path("/hello")
    public String testQeuryParam(@QueryParam("number") int number) {
        AuditRecord auditRecord = auditRecordProducer.produceAuditRecord();
        auditRecord.setEntityType(EntityType.GREETING);
        auditRecord.setValue1("number");
        auditRecord.setValue2(String.valueOf(number));

        return "The typed number is " + number;
    }


}
