package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.vaadin.flow.component.button.Button;

abstract class NavBarAbstractButton extends Button {

    NavBarAbstractButton(String text) {
        super(text);
        setClassName("lr-buttons");
        setListener();
    }

    abstract void setListener();
}
