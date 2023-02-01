package com.onofftaxi.frontend.views.driverViews;

import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

@Push
@Route(value = "driver")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DriverView extends Div {

    private MainView mainView;

    public DriverView(@Autowired DriverService driverService, @Autowired AdvertisementService advertisementService) throws URISyntaxException {
        mainView = new MainView(driverService,advertisementService);

        add(NavBarOperator.getMainNavBar(driverService));
        add(mainView.getAdField(advertisementService));
        add(getMainButtons());

        add(mainView.getAdField(advertisementService));
        add(mainView.getFooter());
    }

    public VerticalLayout getMainButtons() {
        VerticalLayout mainButtons = new VerticalLayout();
        mainButtons.setSizeFull();

        Button accountTile = new Button();
        accountTile.setClassName("lr-buttons");
        Button settingTile = new Button();
        settingTile.setClassName("lr-buttons");

        HorizontalLayout firstHorizontal = new HorizontalLayout();
        HorizontalLayout secondHorizontal = new HorizontalLayout();
        firstHorizontal.setSizeFull();
        secondHorizontal.setSizeFull();

        accountTile.setIcon(new Icon(VaadinIcon.USER_CHECK));
        accountTile.setText("Moje konto");
        accountTile.setSizeFull();

        settingTile.setIcon(new Icon(VaadinIcon.COG));
        settingTile.setSizeFull();
        settingTile.setText("Ustawienia");

        firstHorizontal.add(accountTile, settingTile);
        mainButtons.add(firstHorizontal, secondHorizontal);

        accountTile.addClickListener(e -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/driver/account\", \"_self\");");
        });
        settingTile.addClickListener(e -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/driver/settings\", \"_self\");");
        });

        return mainButtons;
    }

    public Image getStatusImage(DriverService driverService) {
        Image statusImage;
        if (driverService.getDriver().get().getStatus().equals(Status.ON)) {
            statusImage = new Image(
                    "/images/statuson.jpg", "statusOn");
            statusImage.setWidthFull();
            statusImage.setHeight("10em");
        } else if (driverService.getDriver().get().getStatus().equals(Status.OFF)) {
            statusImage = new Image(
                    "/images/statusoff.jpg", "statusOff");
            statusImage.setWidthFull();
            statusImage.setHeight("10em");
        } else {
            statusImage = new Image(
                    "/images/statusbusy.jpg", "statusBusy");
            statusImage.setWidthFull();
            statusImage.setHeight("10em");
        }
        return statusImage;
    }
}
