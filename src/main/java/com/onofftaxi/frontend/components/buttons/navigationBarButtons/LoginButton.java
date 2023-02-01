package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.vaadin.flow.component.UI;

public class LoginButton extends NavBarAbstractButton {

    public LoginButton() {
        super("Zaloguj");
        this.setId("log-btn");
    }

    @Override
    void setListener() {
        this.addClickListener(e ->
                UI.getCurrent()
                        .getPage()
                        .executeJavaScript("window.open(\"/login\", \"_self\");"));
    }


}
