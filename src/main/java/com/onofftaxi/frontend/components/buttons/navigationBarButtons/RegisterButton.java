package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.onofftaxi.frontend.components.dialogs.LoginPatternDialog;

public class RegisterButton extends NavBarAbstractButton {

    public RegisterButton() {
        super("Dodaj konto");
        this.setId("registerBtn");
    }

    @Override
    void setListener() {
        this.addClickListener(e -> new LoginPatternDialog().open());
    }
}
