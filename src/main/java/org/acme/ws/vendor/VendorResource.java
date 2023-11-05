package org.acme.ws.vendor;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/vendors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendorResource {

    @Inject
    private VendorService vendorService;

    @GET
    public Set<Vendor> getVendors() {
        return vendorService.list();
    }

    @GET
    @Path("/{id}")
    public Response getVendor(@PathParam("id") Long id) {
        Vendor vendor = vendorService.get(id);
        if (vendor != null) {
            return Response.ok(vendor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addVendor(Vendor vendor) {
        Vendor newVendor = vendorService.create(vendor);
        return Response.status(Response.Status.CREATED).entity(newVendor).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateVendor(@PathParam("id") Long id, Vendor vendor) {
        Vendor updatedVendor = vendorService.update(id, vendor);
        if (updatedVendor != null) {
            return Response.ok(updatedVendor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteVendor(@PathParam("id") Long id) {
        vendorService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
