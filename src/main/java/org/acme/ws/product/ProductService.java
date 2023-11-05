package org.acme.ws.product;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.ws.generic.AbstractService;

@ApplicationScoped
public class ProductService extends AbstractService<Product> {

    @PostConstruct
    @Override
    public void init() {
        entities.add(new Product(1L, "Laptop", 999.99, "Electronics"));
        entities.add(new Product(2L, "Smartphone", 599.99, "Electronics"));
        entities.add(new Product(3L, "Coffee Maker", 49.99, "Home Appliances"));
    }
}
