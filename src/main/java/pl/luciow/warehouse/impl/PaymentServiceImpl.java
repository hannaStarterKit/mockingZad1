/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.impl;

import pl.luciow.warehouse.PaymentProcessor;
import pl.luciow.warehouse.PaymentService;
import pl.luciow.warehouse.model.Payment;
import pl.luciow.warehouse.util.Validator;
import pl.luciow.warehouse.util.ValidatorUtils;

/**
 *
 * @author Mariusz
 */
public class PaymentServiceImpl implements PaymentService {

    private Validator<Payment> validator;

    private PaymentProcessor paymentProcessor;

    public Long processPayment(Payment payment) throws Exception {
        ValidatorUtils.validate(payment, validator);
        return paymentProcessor.processPayment(payment);
    }

}
