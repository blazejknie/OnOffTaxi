package com.onofftaxi.frontend.views;

import com.onofftaxi.backend.model.Advertisement;
import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.AreaField;
import com.onofftaxi.frontend.components.dialogs.AppInfoDialog;
import com.onofftaxi.frontend.components.frontendStrings.CookieSearcher;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.views.driverViews.MainViewDriverDiv;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.shared.ui.LoadMode;
import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.Optional;

/**
 * annotation @push generate
 * Error: message length " {}; window.Vaadin.Flow = window.Vaadin.Flow " is not a number
 */
@Push
@PageTitle("ON/OFF")
@PWA(name = "OnOffTaxi", shortName = "OnOff", description = "Aktywne taksówki w twojej okolicy")
@Route(value = "")
@HtmlImport("frontend://styles/shared-styles.html")
@HtmlImport(value = "frontend://styles/custom-dialog-styles.html", loadMode = LoadMode.LAZY)
@JavaScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyArNsJPo15MeDI2UDtSuRnG2YdhF_SM6Ts&libraries=places")
@Viewport("user-scalable=no,initial-scale=1.0")
public class MainView extends Div {

    private Div drivers = new Div();
    AreaField areaField = new AreaField();
    private CookieSearcher cookieSearcher = new CookieSearcher();
    private DriverService driverService;
    private AdvertisementService advertisementService;

    public MainView(@Autowired DriverService driverService, @Autowired AdvertisementService advertisementService) {
        this.driverService = driverService;
        this.advertisementService = advertisementService;
        AppInfoDialog appInfoDialog = new AppInfoDialog();

        if (VaadinService.getCurrentRequest().getCookies() != null) {
            if (!cookieSearcher.getCookieByName("AcceptCookie").isPresent()) {
                appInfoDialog.open();
            }
        } else {
            appInfoDialog.open();
        }

        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.setClassName("page");
        setClassName("main-layout");

        mainLayout.add(NavBarOperator.getNavigationBar(driverService));
        Image adField = getAdField(advertisementService);
        mainLayout.add(adField);

        Div locationRefreshHlayout = new Div();
        locationRefreshHlayout.setClassName("location-refresh-hlayout");
        setLocationField(locationRefreshHlayout);

        mainLayout.add(locationRefreshHlayout);
        mainLayout.add(drivers);
        mainLayout.add(getAdField(advertisementService));

        mainLayout.add(getFooter());
        add(mainLayout);
    }

    private Button getRefreshButton(DriverService driverService) {
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.setClassName("refresh-button");
        refreshButton.addClickListener(event -> {
            drivers.removeAll();
            driverService.getAllSortedByStatus().forEach(driver -> {
                Coordinate coord = new Coordinate(areaField.getPlace().getLng(), areaField.getPlace().getLat());
                drivers.add(MainViewDriverDiv.setDriversDiv(driver, coord));
            });
        });
        return refreshButton;
    }

    private void setLocationField(Div hLayout) {
        TextField location = areaField.getContent();
        Button action = new Button("szukaj", VaadinIcon.SEARCH.create());
        action.setClassName("actionLocationButton");

        location.setClassName("location-field");
        location.setPlaceholder("Wpisz miasto");
        location.setClearButtonVisible(true);
        location.setRequiredIndicatorVisible(true);
        location.addKeyPressListener(Key.ENTER, event ->
        {
            if (location.getValue() != null) {
                action.click();
            }
        });

        action.addClickListener(event -> {
            try {
                String district = areaField.getPlace().getDistrict();
                drivers.removeAll();
                driverService.getAllFromDistrict(district).forEach(driver -> {
                    Coordinate coord = new Coordinate(areaField.getPlace().getLng(), areaField.getPlace().getLat());
                    drivers.add(MainViewDriverDiv.setDriversDiv(driver, coord));
                });
            } catch (Exception e) {
               // getAlert();
            }
        });

        Button userLocation = new Button("", VaadinIcon.CROSSHAIRS.create());
        userLocation.setClassName("actionLocationButton");
        HorizontalLayout buttons = new HorizontalLayout(action, userLocation, getRefreshButton(driverService));
        buttons.setId("mainButtons");
        hLayout.add(areaField, buttons);
    }

    public Image getAdField(AdvertisementService advertisementService) {
        Optional<Advertisement> advertisement = advertisementService.getRandomAd();
        if (advertisement.isPresent()) {
            Image adField = new Image(advertisement.get().getImageUrl(), advertisement.get().getAltText());
            adField.setClassName("promo-field");
            adField.addClickListener(event -> {
                UI.getCurrent().getPage().executeJavaScript("window.open(\"" + advertisement.get().getSiteUrl() + "\", \"_blank\");");
            });
            return adField;
        }
        return new Image("", "broken ad field");
    }

    public Div getFooter() {
        Div footer = new Div();
        footer.setClassName("botom-div");
        footer.add(getContactButton());
        footer.add(getAdButton());
        footer.add(getStatuteButton());
        footer.add(getInfoButton());
        footer.add(getSocialButton());
        return footer;
    }

    public static Notification getAlert() {
        return Notification.show("Uzupełnij wymagane pola lub wpisz poprawne dane.");
    }

    //Ireneuszu działaj cuda! https://www.signs.pl/html/a/target.php
    public static Button getContactButton() {
        Button contactButton = new Button("Kontakt", new Icon(VaadinIcon.AT));
        contactButton.setClassName("contact-button");
        contactButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/contact\", \"_self\");");
        });
        return contactButton;
    }

    public static Button getStatuteButton() {
        Button contactButton = new Button("Regulamin", new Icon(VaadinIcon.INFO));
        contactButton.setClassName("contact-button");
        contactButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/regulamin\", \"_self\");");
        });
        return contactButton;
    }

    private Button getAdButton() {
        Button contactButton = new Button("Reklama", new Icon(VaadinIcon.PHONE));
        contactButton.setClassName("contact-button");
        contactButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/reklama\", \"_self\");");
        });
        return contactButton;
    }

    public static Button getInfoButton() {
        Button contactButton = new Button("Dla kierowców", new Icon(VaadinIcon.GROUP));
        contactButton.setClassName("contact-button");
        contactButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/info\", \"_self\");");
        });
        return contactButton;
    }

    private Button getSocialButton() {
        Button socialButton = new Button("Social Media", new Icon(VaadinIcon.FACEBOOK));
        socialButton.setClassName("contact-button");
        socialButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript(
                    "window.open(\"https://www.facebook.com/onofftaxi\", \"_blank\");");
        });
        return socialButton;
    }

}
