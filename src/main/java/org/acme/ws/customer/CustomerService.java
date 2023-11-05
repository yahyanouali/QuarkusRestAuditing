package org.acme.ws.customer;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.ws.generic.AbstractService;
@ApplicationScoped
public class CustomerService extends AbstractService<Customer> {

    @PostConstruct
    @Override
    public void init() {
        entities.add(new Customer(1L, "John Doe", "john@example.com"));
        entities.add(new Customer(2L, "Jane Roe", "jane@example.com"));
        entities.add(new Customer(3L, "Sam Smith", "sam@example.com"));
    }
}

