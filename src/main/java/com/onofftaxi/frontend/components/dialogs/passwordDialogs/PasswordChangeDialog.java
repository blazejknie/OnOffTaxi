package com.onofftaxi.frontend.components.dialogs.passwordDialogs;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.dialogs.BaseDialog;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;

public class PasswordChangeDialog extends BaseDialog {

    /**
     * user type and submit his new password on this dialog
     *
     * @param textFieldsOperator
     * @param driver
     * @param driverService
     */
    public PasswordChangeDialog(TextFieldsOperator textFieldsOperator,
                                DriverDto driver,
                                DriverService driverService) {

        textFieldsOperator.getDriverBinder().getBean().setPassword("");
        PasswordField newPassword = textFieldsOperator.getPassword();
        PasswordField confirmPass = textFieldsOperator.getConfirmPassword();
        this.setNoLabel(newPassword, confirmPass);

        textFieldsOperator.setBindingForConfirmPassword();
        textFieldsOperator.setBindingForPassword();

        this.setDialogPasswordFieldClassName(newPassword, confirmPass);

        Button changePassButton = new Button("Zmień hasło");
        this.setDialogButtonsClassName("dialog-button", changePassButton);

        changePassButton.addClickShortcut(Key.ENTER);
        changePassButton.addClickListener(e ->
                buttonListenerLogic(textFieldsOperator, driver, driverService, newPassword, confirmPass));

        add(newPassword, confirmPass, changePassButton);
        this.addDetachListener(e -> textFieldsOperator.removePasswordBinding());
    }

    /**
     * listener saving new password in  database
     *
     * @param textFieldsOperator
     * @param driver
     * @param driverService
     * @param newPassword
     * @param confirmPass
     */

    private void buttonListenerLogic(TextFieldsOperator textFieldsOperator, DriverDto driver, DriverService driverService, PasswordField newPassword, PasswordField confirmPass) {
        if (newPassword.getValue().equals(confirmPass.getValue()) && textFieldsOperator.getDriverBinder().validate().isOk()) {
            driver.setPassword(newPassword.getValue());
            driverService.changeUserPassword(driver, newPassword.getValue());
            this.close();
            Notification.show("Hasło zostało poprawnie zmienione.");
        } else {
            Notification.show("Podaj poprawne dane");
        }
        textFieldsOperator.removePasswordBinding();
        textFieldsOperator.getDriverBinder().getBean().setPassword("");
    }
}
