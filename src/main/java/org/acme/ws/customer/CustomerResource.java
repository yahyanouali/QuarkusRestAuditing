package org.acme.ws.customer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auditing.Auditable;
import org.acme.auditing.domain.Action;
import org.acme.auditing.domain.EntityType;

import java.util.Set;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    @Auditable(entityType = EntityType.CUSTOMER)
    @GET
    public Set<Customer> getCustomers() {
        return customerService.list();
    }

    @Auditable(entityType = EntityType.CUSTOMER)
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") Long id) {
        Customer customer = customerService.get(id);
        if (customer != null) {
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Auditable(action = Action.CREATE)
    @POST
    public Response addCustomer(Customer customer) {
        Customer newCustomer = customerService.create(customer);
        return Response.status(Response.Status.CREATED).entity(newCustomer).build();
    }

    @Auditable(action = Action.UPDATE)
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, Customer customer) {
        Customer updatedCustomer = customerService.update(id, customer);
        if (updatedCustomer != null) {
            return Response.ok(updatedCustomer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @Auditable(action = Action.DELETE)
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        customerService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
