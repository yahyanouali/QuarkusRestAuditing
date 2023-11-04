package org.acme.ws;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@ApplicationScoped
public class ItemService {

    private Set<Item> items = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));


    @PostConstruct
    void init() {
        // Initialize the set with three items
        items.add(new Item(1L, "Item 1", "Description for Item 1"));
        items.add(new Item(2L, "Item 2", "Description for Item 2"));
        items.add(new Item(3L, "Item 3", "Description for Item 3"));
    }

    public Item createItem(Item item) {
        items.add(item);
        return item;
    }

    public Set<Item> listItems() {
        return items;
    }

    public Item getItem(Long id) {
        return items.stream().filter(item -> id.equals(item.getId())).findFirst().orElse(null);
    }

    public Item updateItem(Long id, Item item) {
        deleteItem(id);
        item.setId(id);
        items.add(item);
        return item;
    }

    public void deleteItem(Long id) {
        items.removeIf(item -> id.equals(item.getId()));
    }
}
