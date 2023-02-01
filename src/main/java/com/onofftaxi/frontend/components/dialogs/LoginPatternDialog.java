package com.onofftaxi.frontend.components.dialogs;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;

public class LoginPatternDialog extends BaseDialog {

    public LoginPatternDialog() {

        Button driverRegisterButton = new Button("Dodaj konto kierowcy", new Icon(VaadinIcon.TAXI));
        Button customerRegisterButton = new Button("Dodaj konto pasażera", new Icon(VaadinIcon.USER));
        customerRegisterButton.setDisableOnClick(true);
        driverRegisterButton.setId("drv-reg-btn");


        setDialogButtonsClassName("dialog-button", customerRegisterButton, driverRegisterButton);
        customerRegisterButton.addClickListener(e -> {
            Notification.show("Opcja dodania konta pasażera jest aktualnie niedostępna");
        });

        driverRegisterButton.addClickListener(e -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/register\", \"_self\");");
        });

        add(driverRegisterButton, customerRegisterButton);
    }
}
