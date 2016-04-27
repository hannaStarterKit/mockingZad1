/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import pl.luciow.warehouse.PaymentProcessor;
import pl.luciow.warehouse.model.Payment;
import pl.luciow.warehouse.util.PaymentValidator;
import pl.luciow.warehouse.util.ValidatorException;
import pl.luciow.warehouse.util.ValidatorUtils;

/**
 *
 * @author Mariusz
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

	@InjectMocks
	private PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();

	@Mock
	private PaymentProcessor paymentProcessorMock;

	@Mock
	private PaymentValidator paymentValidatorMock;

	@Before
	public void prapareMocks() {

	}

	@Test // (expected = ValidatorException.class)
	public void processPaymentTest() throws Exception {

		// given

		Mockito.doAnswer(new Answer<Void>() {

			public Void answer(InvocationOnMock invocation) {
				List<String> errors = (List<String>) invocation.getArguments()[1];
				errors.add("Name is Null");
				// Mock mock = (Mock) invocation.getMock();
				return null;
			}
		}).when(paymentValidatorMock).validate(Mockito.any(Payment.class), Mockito.any(List.class));

		try {
			// when
			paymentServiceImpl.processPayment(new Payment());

		} catch (ValidatorException e) {
			// then
			Mockito.verify(paymentProcessorMock, Mockito.times(0)).processPayment(Mockito.any(Payment.class));
		}

	}

}
