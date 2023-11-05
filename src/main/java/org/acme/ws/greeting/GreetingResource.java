package org.acme.ws.greeting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.acme.auditing.AuditRecordProducer;
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
        auditRecord.addEntityType(EntityType.GREETING);
        auditRecord.addValue1("number");
        auditRecord.addValue2(String.valueOf(number));

        return "The typed number is " + number;
    }


}
