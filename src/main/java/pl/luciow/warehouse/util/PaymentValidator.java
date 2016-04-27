/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.util;

import java.util.List;
import pl.luciow.warehouse.model.Payment;

/**
 *
 * @author Mariusz
 */
public class PaymentValidator implements Validator<Payment> {

    public void validate(Payment object, List<String> errors) {
        if (object.getName() == null) {
            errors.add("Name is null");
        }
    }

}
