package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.vaadin.flow.component.UI;

public class AccountButton extends NavBarAbstractButton {

    public AccountButton() {
        super("Twoje konto");
    }

    @Override
    void setListener() {
        this.addClickListener(event ->
                UI.getCurrent()
                        .getPage()
                        .executeJavaScript("window.open(\"/driver/account\", \"_self\");"));
    }
}
