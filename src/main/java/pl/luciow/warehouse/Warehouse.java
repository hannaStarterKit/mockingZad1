/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse;

import java.util.List;
import pl.luciow.warehouse.model.Item;
import pl.luciow.warehouse.model.NotEnoughItemsException;

/**
 *
 * @author Mariusz
 */
public interface Warehouse {

    public void addItems(List<Item> items);

    public List<Item> removeItems(List<Item> items) throws NotEnoughItemsException;

    public List<Item> getItems();
}
