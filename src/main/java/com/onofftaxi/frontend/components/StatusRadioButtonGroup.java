package com.onofftaxi.frontend.components;

import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

@JavaScript("frontend://notify.js")
public class StatusRadioButtonGroup extends RadioButtonGroup<Status> {

    private DriverService driverService;

    private final Status[] ITEMS = new Status[]{Status.ON,Status.BUSY,Status.OFF};
    private final String CLASS_NAME = "radio-buttons";

    private Status[] getItems() {
        return ITEMS;
    }

    public StatusRadioButtonGroup(@Autowired DriverService driverService) {
        setClassName(CLASS_NAME);

        //todo: refactor.wait(MIŁOWITcommit miłowitCommit)
        this.driverService = driverService;

        Optional<DriverDto> optionalDriver = this.driverService.getDriver();
        DriverDto driver = null;
        if (optionalDriver.isPresent()){
            driver = optionalDriver.get();
        }
        VaadinSession session = VaadinSession.getCurrent();
        Page page = UI.getCurrent().getPage();
        //

        setItems(getItems());
        setValue(Objects.requireNonNull(driver).getStatus());

        DriverDto finalDriver = driver;
        addValueChangeListener(e -> runStatusTimer(finalDriver, session, page, e));

    }

    private void runStatusTimer(DriverDto driver,
                                VaadinSession session,
                                Page page,
                                ComponentValueChangeEvent<RadioButtonGroup<Status>,
                                Status> e) {

        TimerOperator timerOperator = new TimerOperator();
        try {
            if (e.getValue().equals(Status.ON)) {
                page.executeJavaScript("notify('ON')");
                timerOperator.setDriverOnTime(driver, this.driverService, this, session, Status.BUSY);
            } else if (e.getValue().equals(Status.BUSY)) {
                page.executeJavaScript("notify('BUSY')");
                timerOperator.setDriverOnTime(driver, this.driverService, this, session, Status.OFF);
            } else {
               // page.executeJavaScript("ServiceWorkerRegistration.showNotification(\"Status OFF\")");
                page.executeJavaScript("notify('OFF')");
            }

            driver.setStatus(e.getValue());
            this.driverService.update(driver);
        } catch (Exception er) {
            System.out.println("Multithreading exception to fix " + er.getCause());
        }
    }

}
