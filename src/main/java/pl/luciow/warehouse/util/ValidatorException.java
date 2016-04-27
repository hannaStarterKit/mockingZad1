/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse.util;

import java.util.List;

/**
 *
 * @author Mariusz
 */
public class ValidatorException extends Exception {

    private final List<String> reasons;

    public ValidatorException(List<String> reasons) {
        this.reasons = reasons;
    }

    public List<String> getReasons() {
        return reasons;
    }

}
