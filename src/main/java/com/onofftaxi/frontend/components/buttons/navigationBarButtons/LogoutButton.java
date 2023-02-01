package com.onofftaxi.frontend.components.buttons.navigationBarButtons;

import com.onofftaxi.backend.model.Driver;
import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.frontendStrings.CookieSearcher;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;

import java.util.Objects;

public class LogoutButton extends NavBarAbstractButton {

    private DriverService driverService;
    private CookieSearcher cookieSearcher = new CookieSearcher();
    private DriverDto driver;

    public LogoutButton(DriverService driverService) {
        super("Wyloguj");
        this.driverService = driverService;
        if (driverService.getDriver().isPresent()) {
            driver = driverService.getDriver().get();
        }
    }

    @Override
    void setListener() {
        this.addClickListener(e -> {
            driver.setStatus(Status.OFF);
            driverService.update(driver);
            if (VaadinService.getCurrentRequest().getCookies() != null) {
                if (cookieSearcher.getCookieByName("remember-me").isPresent()) {
                    Objects.requireNonNull(cookieSearcher.getCookieByName("remember-me").get()).setMaxAge(0);
                }
            }
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/perform_logout\", \"_self\");");
        });
    }
}
