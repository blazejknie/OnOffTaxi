package com.onofftaxi.frontend.components.dialogs.passwordDialogs;

import com.onofftaxi.backend.model.CustomUserDetails;
import com.onofftaxi.backend.model.Driver;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.dialogs.BaseDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinService;

import java.util.Objects;

class DeleteAccountDialog extends BaseDialog {

    /**
     * creating popup with only delete account button
     * open if password correct
     *
     * @param driverService
     */
    private DriverDto driver;

    DeleteAccountDialog(DriverService driverService) {
        if (driverService.getDriver().isPresent())
            driver = driverService.getDriver().get();

        Button deleteButton = new Button("Usuń konto", new Icon(VaadinIcon.FILE_REMOVE));
        setDialogButtonsClassName("dialog-button", deleteButton);
        add(deleteButton);

        deleteButton.addClickListener(e -> {
            buttonListenerLogic(driverService);
        });
    }

    /**
     * delete -> //todo:
     *
     * @param driverService cookie removed also
     * @see CustomUserDetails -> block/expire
     * now its remove driver from database
     */

    private void buttonListenerLogic(DriverService driverService) {
        driverService.deleteDriver(driver);
        if (VaadinService.getCurrentRequest().getCookies() != null) {
            if (getCookieSearcher().getCookieByName("remember-me").isPresent()) {
                Objects.requireNonNull(getCookieSearcher().getCookieByName("remember-me").get()).setMaxAge(0);
            }
        }
        this.close();
        UI.getCurrent().getPage().executeJavaScript("window.open(\"/perform_logout\", \"_self\");");
    }
}
