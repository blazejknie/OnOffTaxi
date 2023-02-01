package com.onofftaxi.frontend.views.driverViews;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.frontend.components.validators.WordsValidator;
import com.onofftaxi.backend.model.Driver;
import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.AreaField;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.LoadMode;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
@Push
@Route(value = "driver/account")
@HtmlImport(value = "frontend://styles/shared-styles.html",loadMode = LoadMode.INLINE)
@HtmlImport(value = "frontend://styles/custom-dialog-styles.html",  loadMode = LoadMode.LAZY)
@JavaScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyArNsJPo15MeDI2UDtSuRnG2YdhF_SM6Ts&libraries=places")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DriverAccountView extends Div {

    private DriverView driverView;
    private MainView mainView;
    private DriverService driverService;
    private TextFieldsOperator textFieldsOperator;
    private AreaField areaField;
    private DriverDto driver;

    public DriverAccountView(@Autowired DriverService driverService, @Autowired TextFieldsOperator textFieldsOperator,
                             @Autowired AdvertisementService advertisementService) throws URISyntaxException, IOException {
        if (driverService.getDriver().isPresent()) {
            this.driver = driverService.getDriver().get();
        }
        this.driverService = driverService;
        this.textFieldsOperator = textFieldsOperator;
        driverView = new DriverView(driverService,advertisementService);
        mainView = new MainView(driverService,advertisementService);
        areaField = textFieldsOperator.getAreaField();
        textFieldsOperator.setBindingForOperativeCityName();

        setClassName("main-layout");
        textFieldsOperator.getDriverBinder().setBean(driver);


        add(NavBarOperator.getMainNavBar(driverService));
        add(mainView.getAdField(advertisementService));

        add(myAccount());

        add(driverView.getMainButtons());
        add(mainView.getAdField(advertisementService));

        add(mainView.getFooter());
    }
    public VerticalLayout myAccount() throws IOException {

        VerticalLayout hlayout = new VerticalLayout();
        TextArea description = textFieldsOperator.getDescription();
        description.setClassName("driver-description-field");
        description.setEnabled(false);
        textFieldsOperator.setBindingForDescription();


        TextField number = textFieldsOperator.getPhoneNumber();
        number.setEnabled(false);
        textFieldsOperator.setBindingForEditPhoneNumber();
        number.setClassName("driver-description-field");


        TextField locationField = areaField.getContent();
        textFieldsOperator.setBindingForOperativeCityName();

        locationField.setEnabled(false);
        locationField.setClearButtonVisible(true);
//        locationField.setValue(driver.getPlace().getName());
        locationField.setPlaceholder("*Podaj miejscowość licencji.");
        locationField.setLabel("Miejscowość (licencja taxi): ");
        locationField.addClassName("driver-description-field");

        Button saveChangesButton = new Button("Zapisz", new Icon(VaadinIcon.ENTER));
        saveChangesButton.setClassName("buttons");
        saveChangesButton.setVisible(false);
        Button modifyButton = new Button("EDYTUJ", new Icon(VaadinIcon.EDIT));
        modifyButton.setClassName("buttons");
        hlayout.setClassName("account-view");
        hlayout.add(description, number,locationField);
        hlayout.add(modifyButton, saveChangesButton);

        modifyButton.addClickListener(e -> {
            saveChangesButton.setVisible(true);
            description.setEnabled(true);
            number.setEnabled(true);
            modifyButton.setVisible(false);
            locationField.setEnabled(true);
        });
        saveChangesButton.addClickListener(e -> {

            if (textFieldsOperator.getDriverBinder().validate().isOk()) {
                driver.setPlaceName(areaField.getPlace().getName());
                driver.setPlaceDistrict(areaField.getPlace().getDistrict());
                Point point = areaField.getPlace().createPoint();
                driver.setPlaceLocation(point);
                driver.setGeolocationPosition(point);
                driverService.update(textFieldsOperator.getDriverBinder().getBean());
                UI.getCurrent().getPage().reload();
            } else {
                Notification.show("Podaj poprawne dane");
            }
        });
        return hlayout;
    }




}
