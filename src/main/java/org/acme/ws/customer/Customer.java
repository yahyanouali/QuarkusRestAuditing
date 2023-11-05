package org.acme.ws.customer;

import lombok.*;
import org.acme.ws.generic.BaseEntity;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {
    private Long id;
    private String name;
    private String email;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
