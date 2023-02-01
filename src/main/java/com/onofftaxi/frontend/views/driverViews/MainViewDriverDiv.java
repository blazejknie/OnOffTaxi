package com.onofftaxi.frontend.views.driverViews;

import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vividsolutions.jts.geom.Coordinate;

public class MainViewDriverDiv {

    public static HorizontalLayout setDriversDiv(DriverDto driver, Coordinate userLocation) {
        HorizontalLayout driverDiv = new HorizontalLayout();
        driverDiv.setClassName("driver-div");

        VerticalLayout imageStatusDiv = new VerticalLayout();
        imageStatusDiv.setClassName("image-status-div");
        imageStatusDiv.setSizeUndefined();
        Image driverImage;

        if (driver.getImageUrl() != null) {
            driverImage = new Image(driver.getImageUrl(), "broken image url");
        } else {
            driverImage = new Image("http://www.eswinoujscie.pl/wp-content/uploads/2019/03/taxi-3504010_1920.jpg", "broken image url");
        }
        driverImage.setClassName("driver-image");

        VerticalLayout servicesLayout = new VerticalLayout();
        servicesLayout.setClassName("servicesLayout");
        servicesLayout.setSizeUndefined();
        VerticalLayout status = new VerticalLayout();
        Button driverStatus = new Button(driver.getStatus().toString());
        driverStatus.setSizeUndefined();
        Button call = new Button(VaadinIcon.PHONE.create(), event -> getPhoneDialog(driver).open());
        call.setClassName("main-call-button");


        Label driverCity = new Label(driver.getPlaceName());
        status.setClassName("driver-status");
        status.add(driverStatus);

        VerticalLayout layout = new VerticalLayout();
        H2 driver_name = new H2(driver.getDisplayedName());
        driver_name.setClassName("driver-name");
        layout.add(driver_name);
        layout.setClassName("main-driver-info");
        layout.setSizeUndefined();
        Label driver_description = new Label(driver.getDescription());
        layout.add(driver_description);
        driver_description.setClassName("driver-description");

        Coordinate driverLocation = driver.getGeolocationPosition().getCoordinate();
        String distance = String.format("%.2f", getDistance(userLocation, driverLocation));

        H4 driver_localization = new H4(distance + " km od wskazanego miejsca");
        driver_localization.setClassName("driver-localization");
        layout.add(driver_localization);

        if (driver.getStatus().equals(Status.ON)) {
            driverStatus.setClassName("driver-status-button-on");
        } else if (driver.getStatus().equals(Status.OFF)) {
            driverStatus.setClassName("driver-status-button-off");
            driver.setPhone("");
            driver_localization.setVisible(false);
            call.setEnabled(false);
            call.setClassName("main-call-button-disabled");
        } else {
            driverStatus.setClassName("driver-status-button-busy");
            call.setClassName("main-call-button-busy");
        }
        H3 number = new H3(driver.getPhone());
        number.setClassName("number-label");

        servicesLayout.add(driverCity, number);
        imageStatusDiv.setSizeUndefined();
        imageStatusDiv.add(driverImage, status);
        driverDiv.add(imageStatusDiv);

        driverDiv.add(layout);
        driverDiv.add(servicesLayout);

        driverDiv.add(call);

        driverDiv.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER,
                driverImage, servicesLayout, layout, call, number);

        return driverDiv;
    }

    private static Dialog getPhoneDialog(DriverDto driver) {
        Dialog phoneDialog = new Dialog();
        Anchor callAnchor = new Anchor("tel:" + driver.getPhone(), VaadinIcon.PHONE.create());
        VerticalLayout phoneDialogLayout = new VerticalLayout();
        phoneDialogLayout.setClassName("phone-dialog-layout");
        phoneDialogLayout.add(new Label("Zadzwoń do"), new Label(driver.getDisplayedName()), callAnchor);
        phoneDialogLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, callAnchor);
        callAnchor.setClassName("call-button");
        phoneDialog.add(phoneDialogLayout);
        return phoneDialog;
    }

    private static double getDistance(Coordinate from, Coordinate to) {
        int radius = 6371;
        double dLng = Math.toRadians(from.x - to.x);
        double dLat = Math.toRadians(from.y - to.y);
        double fromY = Math.toRadians(from.y);
        double toY = Math.toRadians(to.y);

        double haversine = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLng / 2), 2) * Math.cos(fromY) * Math.cos(toY);
        double distance = 2 * radius * Math.asin(Math.sqrt(haversine));
        return distance;
    }
}
