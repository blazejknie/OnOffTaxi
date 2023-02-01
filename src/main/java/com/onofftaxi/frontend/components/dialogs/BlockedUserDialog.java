package com.onofftaxi.frontend.components.dialogs;

import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;

public class BlockedUserDialog extends BaseDialog {

    /**
     * open when user screw his password 5 times
     *
     * @see com.onofftaxi.backend.service.LoginAttemptService
     */

    public BlockedUserDialog() {
        this.isNotCloseOnClickAndEsc();
        add(new H2("Możliwość logowania zablokowana na 24h"));

        Button contactButton = MainView.getContactButton();
        this.setDialogButtonsClassName("dialog-button", contactButton);

        add(contactButton);
    }
}