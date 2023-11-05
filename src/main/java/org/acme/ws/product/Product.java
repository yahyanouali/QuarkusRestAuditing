package org.acme.ws.product;

import lombok.*;
import org.acme.ws.generic.BaseEntity;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    private Long id;
    private String name;
    private double price;
    private String category;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
