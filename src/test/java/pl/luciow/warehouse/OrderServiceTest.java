/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse;

import static org.junit.Assert.*;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Not;

import pl.luciow.warehouse.impl.OrderServiceImpl;
import pl.luciow.warehouse.impl.WarehouseImpl;
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

class MyArgumentMatcherError extends ArgumentMatcher<Mail> {
	public boolean matches(Object o) {
		return ((Mail) o).getContent().equals("Error occured");
	}
}

class MyArgumentMatcherSuccess extends ArgumentMatcher<Mail> {
	public boolean matches(Object o) {
		return ((Mail) o).getContent().equals("Success");
	}
}

public class OrderServiceTest {

	private OrderService orderService;

	@Before
	public void prapareMocks() {

	}

	@Test
	public void fillOrderSuccesTest() {

		// given
		Warehouse warehouseMock = Mockito.mock(Warehouse.class);
		orderService = new OrderServiceImpl(null, null, warehouseMock);
		Order order = new Order();

		//
		try {
			Mockito.when(warehouseMock.removeItems(Mockito.any(List.class))).thenReturn(null);

			orderService.fillOrder(order);
		} catch (Exception e) {
			assertTrue(false);
		}
		assertTrue(true);
	}

	@Test(expected = OrderProcessException.class)
	public void fillOrderThrowTest() throws OrderProcessException, NotEnoughItemsException {

		// given
		Warehouse warehouseMock = Mockito.mock(Warehouse.class);
		orderService = new OrderServiceImpl(null, null, warehouseMock);
		Order order = new Order();

		Mockito.when(warehouseMock.removeItems(Mockito.any(List.class))).thenThrow(new NotEnoughItemsException());
		//when
		orderService.fillOrder(order);

	}

	/*
	 * Zadanie 3.
	 * 
	 * Przetestuj funkcję cancelOrder() w WarehouseImpl ustawiając mock tak,
	 * żeby w wywołaniu addItems() wywołał oryginalną funkcję. Żeby
	 * zaprogramować funkcję zwracającą void, musimy użyć trochę innej składni
	 * niż w poprzednim zadaniu:
	 */
	
	@Test
	public void cancelOrderTest() throws OrderProcessException {

		// given
		Warehouse warehouseMock = Mockito.mock(WarehouseImpl.class);
		orderService = new OrderServiceImpl(null, null, warehouseMock);

		Item item = new Item();
		List<Item> listOfItems = new ArrayList<Item>();
		listOfItems.add(item);

		Order order = new Order();
		order.setItems(listOfItems);

		Mockito.doCallRealMethod().when(warehouseMock).addItems(Mockito.anyList());

		// when
		orderService.cancelOrder(order);

		// then
		Mockito.verify(warehouseMock, Mockito.times(1)).addItems(Mockito.anyList());

	}
	/*
	 * Przetestuj funkcję processPayment() ustawiając mock PaymentService tak,
	 * żeby przy wywołaniu metody processPayment() rzucił wyjątek. Zweryfikuj
	 * czy do metody sendMail() na mock’u MailService trafia mail z treścią
	 * „Error occured”. W tym celu musimy stworzyć klasę dziedziczącą po klasie
	 * ArgumentMatcher i nadpisać metodę matches(Object o); W tej metodzie
	 * musimy sprawdzić czy obiekt‘o’ spełnia nasze wymagania.Po wywołaniu testu
	 * musimy sprawdzić czy wszystko jest ok.W tym celu:
	 */

	@Test
	public void processPaymentThrowTest() throws Exception {

		// given
		PaymentService processPaymentMock = Mockito.mock(PaymentService.class);
		MailService mailServiceMock = Mockito.mock(MailService.class);

		orderService = new OrderServiceImpl(mailServiceMock, processPaymentMock, null);
		Order order = new Order();
		Payment payment = new Payment();

		Mockito.when(processPaymentMock.processPayment(Mockito.any(Payment.class))).thenThrow(new Exception());

		// when
		orderService.processPayment(order, payment);

		// then
		Mockito.verify(mailServiceMock).sendMail((Mail) Mockito.argThat(new MyArgumentMatcherError()));

	}

	@Test
	public void processPaymentSuccessTest() throws Exception {

		// given
		PaymentService processPaymentMock = Mockito.mock(PaymentService.class);
		MailService mailServiceMock = Mockito.mock(MailService.class);

		orderService = new OrderServiceImpl(mailServiceMock, processPaymentMock, null);
		Order order = new Order();

		Mockito.when(processPaymentMock.processPayment(Mockito.any(Payment.class))).thenReturn(Long.valueOf(2));

		// when
		orderService.processPayment(order, Mockito.mock(Payment.class));

		// then
		Mockito.verify(mailServiceMock).sendMail((Mail) Mockito.argThat(new MyArgumentMatcherSuccess()));
	}

}
