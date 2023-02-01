package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.vaadin.flow.component.UI;

public class MainViewButton extends NavBarAbstractButton {

    public MainViewButton() {
        super("Strona główna");
    }

    @Override
    void setListener() {
        this.addClickListener(event ->
                UI.getCurrent()
                        .getPage()
                        .executeJavaScript("window.open(\"/\", \"_self\");"));
    }
}
