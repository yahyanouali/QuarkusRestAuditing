package org.acme.ws;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {
    private final ItemService itemService;

    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @POST
    public Response addItem(Item item) {
        itemService.createItem(item);
        return Response.ok(item).status(Response.Status.CREATED).build();
    }

    @GET
    public Set<Item> getItems() {
        return itemService.listItems();
    }

    @GET
    @Path("/{id}")
    public Response getItem(@PathParam("id") Long id) {
        Item item = itemService.getItem(id);
        if (item != null) {
            return Response.ok(item).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateItem(@PathParam("id") Long id, Item item) {
        Item updatedItem = itemService.updateItem(id, item);
        if (updatedItem != null) {
            return Response.ok(updatedItem).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") Long id) {
        itemService.deleteItem(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

