package org.acme.ws.vendor;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.ws.generic.AbstractService;

@ApplicationScoped
public class VendorService extends AbstractService<Vendor> {

    @PostConstruct
    @Override
    public void init() {
        entities.add(new Vendor(1L, "Tech Supplies Inc.", "+1234567890", "123 Tech Street"));
        entities.add(new Vendor(2L, "Office Essentials Ltd.", "+0987654321", "456 Office Road"));
        entities.add(new Vendor(3L, "Industrial Goods Co.", "+1122334455", "789 Industrial Ave"));
    }
}
