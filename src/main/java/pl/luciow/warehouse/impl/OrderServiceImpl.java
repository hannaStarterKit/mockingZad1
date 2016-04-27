/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.impl;

import java.util.Date;
import java.util.List;
import pl.luciow.warehouse.MailService;
import pl.luciow.warehouse.OrderService;
import pl.luciow.warehouse.PaymentService;
import pl.luciow.warehouse.Warehouse;
import pl.luciow.warehouse.model.Item;
import pl.luciow.warehouse.model.Mail;
import pl.luciow.warehouse.model.NotEnoughItemsException;
import pl.luciow.warehouse.model.Order;
import pl.luciow.warehouse.model.OrderProcessException;
import pl.luciow.warehouse.model.Payment;

/**
 *
 * @author Mariusz
 */
public class OrderServiceImpl implements OrderService {

	private final MailService mailService;

	private final PaymentService paymentService;

	private final Warehouse warehouse;

	public OrderServiceImpl(MailService mailService, PaymentService paymentService, Warehouse warehouse) {
		this.mailService = mailService;
		this.paymentService = paymentService;
		this.warehouse = warehouse;
	}

	public void fillOrder(Order order) throws OrderProcessException {
		List<Item> items = order.getItems();
		try {
			warehouse.removeItems(items);
		} catch (NotEnoughItemsException ex) {
			throw new OrderProcessException(ex);
		}
	}

	public void cancelOrder(Order order) throws OrderProcessException {
		if (order.isCompleted()) {
			throw new OrderProcessException();
		}
		List<Item> items = order.getItems();
		warehouse.addItems(items);
	}

	public void processPayment(Order order, Payment payment) {
		try {
			Long paymentId = paymentService.processPayment(payment);
			payment.setPaymentId(paymentId);
			Mail mail = new Mail();
			mail.setContent("Success");
			mail.setMail(order.getMail());
			mailService.sendMail(mail);
			payment.setPaymentDate(new Date());
			order.setCompleted(true);
		} catch (Exception ex) {
			Mail mail = new Mail();
			mail.setContent("Error occured");
			mail.setMail(order.getMail());
			mailService.sendMail(mail);
		}
	}

}
