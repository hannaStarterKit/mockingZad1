/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luciow.warehouse;

import pl.luciow.warehouse.model.Mail;

/**
 *
 * @author Mariusz
 */
public interface MailService {

    public void sendMail(Mail mail);
}
