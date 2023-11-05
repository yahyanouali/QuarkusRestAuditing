package org.acme.ws.vendor;

import lombok.*;
import org.acme.ws.generic.BaseEntity;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendor extends BaseEntity {
    private Long id;
    private String name;
    private String contactNumber;
    private String address;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
