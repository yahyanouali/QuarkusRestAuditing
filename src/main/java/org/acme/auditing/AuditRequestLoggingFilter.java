package org.acme.auditing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import lombok.NoArgsConstructor;
import org.acme.auditing.domain.AuditRecord;
import org.acme.auditing.domain.AuditableKeys;
import org.acme.auditing.domain.EntityType;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Provider
@Auditable
@NoArgsConstructor
public class AuditRequestLoggingFilter implements ContainerRequestFilter {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Inject
    AuditRecordProducer auditRecordProducer;

    @Context
    private ResourceInfo resourceInfo;
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        AuditRecord auditRecord = auditRecordProducer.produceAuditRecord();

        // Extract User and added it to the AuditRecord
        String host = crc.getHeaders().getFirst("Host");
        auditRecord.addUser(host);

        // Extract action and status and added it to the AuditRecord
        Method resourceMethod = resourceInfo.getResourceMethod();
        if (resourceMethod != null) {
            Auditable auditable = resourceMethod.getAnnotation(Auditable.class);
            if( auditable != null) {
                auditRecord.addAction(auditable.action());
                auditRecord.addStatus(auditable.status());
                auditRecord.addEntityType(auditable.entityType());

            }
        }

        // Extract params and payload
        String id = crc.getUriInfo().getPathParameters().getFirst(AuditableKeys.ID.toLowerCase());
        if (id != null) {
            auditRecord.addId(id);
        }

        // Read the entity from the request
        if (crc.hasEntity() && MediaType.APPLICATION_JSON_TYPE.isCompatible(crc.getMediaType())) {
            String payload = readPayload(crc);
            auditRecord.addMessage(payload);
            Map<String, Object> entityMap = parsePayloadAsMap(payload);

            if (auditRecord.getId() == null && entityMap.containsKey(AuditableKeys.ID.toLowerCase())) {
                auditRecord.addId(entityMap.get(AuditableKeys.ID.toLowerCase()).toString());
            }

            if (entityMap.containsKey(AuditableKeys.EMAIL.toLowerCase())) {
                auditRecord.addEntityType(EntityType.CUSTOMER);
                auditRecord.addValue1(AuditableKeys.EMAIL.toString());
                auditRecord.addValue2(entityMap.get(AuditableKeys.EMAIL.toLowerCase()).toString());

            } else if (entityMap.containsKey(AuditableKeys.ADDRESS.toLowerCase())) {
                auditRecord.addEntityType(EntityType.VENDOR);
                auditRecord.addValue1(AuditableKeys.ADDRESS.toString());
                auditRecord.addValue2(entityMap.get(AuditableKeys.ADDRESS.toLowerCase()).toString());

            } else if (entityMap.containsKey(AuditableKeys.PRICE.toLowerCase())) {
                auditRecord.addEntityType(EntityType.PRODUCT);
                auditRecord.addValue1(AuditableKeys.PRICE.toString());
                auditRecord.addValue2(entityMap.get(AuditableKeys.PRICE.toLowerCase()).toString());

            }
        }

    }

    private static String readPayload(ContainerRequestContext crc) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(crc.getEntityStream());
        String payload = IOUtils.toString(stream, StandardCharsets.UTF_8);
        crc.setEntityStream(IOUtils.toInputStream(payload, StandardCharsets.UTF_8));
        return payload;
    }

    private static Map<String, Object> parsePayloadAsMap(String payload) throws JsonProcessingException {
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};
        return OBJECT_MAPPER.readValue(payload, typeRef);
    }


}
