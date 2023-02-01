package com.onofftaxi.frontend.operators;

import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.StatusRadioButtonGroup;
import com.onofftaxi.frontend.components.buttons.navigationBarButtons.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class NavBarOperator {


    public static HorizontalLayout getNavigationBar(@Autowired DriverService driverService) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("top-div");

        if (driverService.getDriver().isPresent()) {
            layout.add(getMainNavBar(driverService));
        } else {
            layout.add(getUnloggedNav());
        }
        return layout;
    }

    public static VerticalLayout getMainNavBar(DriverService driverService) {
        VerticalLayout driverLayout = new VerticalLayout();
        driverLayout.setClassName("top-div");
        Optional<DriverDto> driver = driverService.getDriver();
        Label driver_info = null;
        if (driver.isPresent()) {
            driver_info = new Label("Witaj " + driver.get().getFirstName() + ", ustaw status:");
            driver_info.setClassName("driver_info");
        }
        driverLayout.add(getDriverNavBar(new LogoutButton(driverService), driver_info, getAccountButton(), new StatusRadioButtonGroup(driverService)));

        return driverLayout;
    }

    private static Button getAccountButton() {
        VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
        HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
        String requestUrl = httpServletRequest.getRequestURL().toString();
        Button accountButton;
        if (!requestUrl.endsWith("/login")
                && !requestUrl.endsWith("/driver/account")
                && !requestUrl.endsWith("/driver/settings")) {
            accountButton = new AccountButton();
        } else {
            accountButton = new MainViewButton();
        }
        return accountButton;
    }

    public static Div getDriverNavBar(Button logOutButton, Label driver_info, Button accountButton, RadioButtonGroup<Status> group) {
        Div topDiv = new Div();
        topDiv.setClassName("top-div");
        VerticalLayout nameStatus = new VerticalLayout();
        nameStatus.add(driver_info, group);
        nameStatus.setSizeUndefined();
        nameStatus.setClassName("top-div-logged");
        VerticalLayout accountLogout = new VerticalLayout();
        accountLogout.add(logOutButton, accountButton);
        accountLogout.setClassName("logged-buttons");
        accountLogout.setSizeUndefined();
        topDiv.add(NavBarOperator.getOurAppLogo(), nameStatus, accountLogout);
        return topDiv;
    }

    public static HorizontalLayout getUnloggedNav() {
        HorizontalLayout topDiv = new HorizontalLayout();
        topDiv.setClassName("top-div");

        VerticalLayout lrButtonsDiv = new VerticalLayout();
        lrButtonsDiv.add(new LoginButton(), new RegisterButton());
        lrButtonsDiv.setClassName("lrButtonsDiv");
        lrButtonsDiv.setSizeUndefined();
        Image logo = getOurAppLogo();
        logo.setClassName("main-logo");
        topDiv.add(logo, lrButtonsDiv);
        return topDiv;
    }

    public static Image getOurAppLogo() {
        Image logo = new Image(
                "/images/logo.png", "logo");
        logo.setClassName("logo");
        logo.addClickListener(e -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/\", \"_self\");");
        });
        logo.setTitle("Strona główna onofftaxi.pl");
        return logo;
    }

}
