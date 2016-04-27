/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse;

import pl.luciow.warehouse.model.Order;
import pl.luciow.warehouse.model.OrderProcessException;
import pl.luciow.warehouse.model.Payment;

/**
 *
 * @author Mariusz
 */
public interface OrderService {

    public void fillOrder(Order order) throws OrderProcessException;

    public void cancelOrder(Order order) throws OrderProcessException;

    public void processPayment(Order order, Payment payment);
}
