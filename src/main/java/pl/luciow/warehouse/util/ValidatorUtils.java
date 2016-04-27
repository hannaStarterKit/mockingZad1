/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariusz
 */
public class ValidatorUtils {

	public static <T> void validate(T object, Validator<T>... validators) throws ValidatorException {

		List<String> errors = new ArrayList<String>();

		for (Validator<T> validator : validators) {
			validator.validate(object, errors);
		}
		if (!errors.isEmpty()) {
			throw new ValidatorException(errors);
		}
	}
}
