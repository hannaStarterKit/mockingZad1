/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.impl;

import java.util.ArrayList;
import java.util.List;
import pl.luciow.warehouse.Warehouse;
import pl.luciow.warehouse.model.Item;
import pl.luciow.warehouse.model.NotEnoughItemsException;

/**
 *
 * @author Mariusz
 */
public class WarehouseImpl implements Warehouse {

    private List<Item> items = new ArrayList<Item>();

    public void addItems(List<Item> items) {
        if (this.items == null) {
            this.items = new ArrayList<Item>();
        }
        this.items.addAll(items);
    }

    public List<Item> removeItems(List<Item> items) throws NotEnoughItemsException {
        for (Item item : items) {
            if (!this.items.contains(item)) {
                throw new NotEnoughItemsException();
            }
        }
        this.items.removeAll(items);
        return items;
    }

    public List<Item> getItems() {
        return items;
    }

}
