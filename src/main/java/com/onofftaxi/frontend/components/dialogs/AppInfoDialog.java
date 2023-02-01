package com.onofftaxi.frontend.components.dialogs;

import com.onofftaxi.frontend.components.frontendStrings.MapOfString;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;

import static com.onofftaxi.frontend.operators.NavBarOperator.getOurAppLogo;

public class AppInfoDialog extends BaseDialog {

    /**
     * it have to be refactored after Milowit commit
     * //todo: refactoring
     */

    public AppInfoDialog() {
        MapOfString map = new MapOfString();
        this.isNotCloseOnClickAndEsc();

        VerticalLayout appInfoVerLay = this.createVerticalDialogLayout();
        appInfoVerLay.setClassName("main-dialog");
        appInfoVerLay.setId("accept-cookie-dlg");

        Label appInfoLabel = new Label(map.get("appInfoDialog"));
        Label localizationInfoLabel = new Label(map.get("appInfoLabel"));

        Button okButton = new Button(map.get("buttonOk"));
        okButton.setId("ok-btn");

        okButton.addClickListener(event -> acceptButtonListener());
        Button contactButton = MainView.getContactButton();
        Button regulaminButton = MainView.getStatuteButton();
        Button infoButton = MainView.getInfoButton();

        setDialogButtonsClassName("footer-button", okButton, contactButton, regulaminButton, infoButton);

        HorizontalLayout footer = new HorizontalLayout(contactButton, regulaminButton, infoButton);
        footer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, contactButton, regulaminButton, infoButton);
        footer.setClassName("dialog-footer");

        Image ourAppLogo = getOurAppLogo();
        ourAppLogo.setClassName("dialog-logo");
        appInfoVerLay.add(ourAppLogo, appInfoLabel, localizationInfoLabel, okButton, footer);

        appInfoVerLay.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, ourAppLogo, appInfoLabel, localizationInfoLabel, okButton);
        add(appInfoVerLay);
    }

    private void acceptButtonListener() {
        Cookie acceptCookie = new Cookie("AcceptCookie", LocalDateTime.now().toString());
        acceptCookie.setMaxAge(300000000);
        VaadinService.getCurrentResponse().addCookie(acceptCookie);
        this.close();
    }

}
