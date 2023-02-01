package com.onofftaxi.frontend.components.dialogs.passwordDialogs;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.dialogs.BaseDialog;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConfirmPasswordDialog extends BaseDialog {

    /**
     * basic confirm dialog
     * with only one passwordfield and button
     * that check if user typed it correct
     *
     * @param text
     * @param textFieldsOperator
     * @param passwordEncoder
     * @param driver
     * @param driverService
     */
    public ConfirmPasswordDialog(String text,
                                 TextFieldsOperator textFieldsOperator,
                                 BCryptPasswordEncoder passwordEncoder,
                                 DriverDto driver,
                                 DriverService driverService) {
        Button button = new Button("Wyślij");
        this.setDialogButtonsClassName("dialog-button", button);
        PasswordField password = textFieldsOperator.getPassword();
        this.setNoLabel(password);
        this.setDialogPasswordFieldClassName(password);
        password.setValue("");

        add(password, button);

        button.addClickListener(event -> {
            buttonListenerLogic(text, textFieldsOperator, passwordEncoder, driver, driverService, password);
        });
    }

    /**
     * todo: if text equals is so shitty
     *
     * @param text
     * @param textFieldsOperator
     * @param passwordEncoder
     * @param driver
     * @param driverService
     * @param password
     */

    private void buttonListenerLogic(String text, TextFieldsOperator textFieldsOperator,
                                     BCryptPasswordEncoder passwordEncoder, DriverDto driver,
                                     DriverService driverService, PasswordField password) {
        if (driverService.getDriver().isPresent()) {
            if (passwordEncoder.matches(password.getValue(), driverService.getDriver().get().getPassword())) {
                if (text.equals("Usuń konto")) {
                    this.close();
                    new DeleteAccountDialog(driverService).open();
                } else if (text.equals("Zmiana hasła")) {
                    this.close();
                    new PasswordChangeDialog(textFieldsOperator, driver, driverService).open();
                }
            } else {
                Notification.show("Podaj poprawne hasło");
            }
        }
    }
}
