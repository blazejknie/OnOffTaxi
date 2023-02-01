package com.onofftaxi.frontend.components;

import com.onofftaxi.backend.model.Status;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;

import java.util.Timer;
import java.util.TimerTask;

class TimerOperator {

    private Timer timer;

    /**
     * STATUS_ON_DELAY = time of driver status ON
     * STATUS_BUSY_DELAY = time of driver status BUSY
     * OFF status after busy delay
     * 10 000 milis = 10 seconds
     */

    private final long STATUS_ON_DELAY = 10000; //
    private final long STATUS_BUSY_DELAY = 20000;


    TimerOperator() {
        timer = new Timer();
    }

    void setDriverOnTime(DriverDto driverOnTime,
                         DriverService driverService,
                         RadioButtonGroup<Status> r,
                         VaadinSession session,
                         Status status) {
        long delay;
        if (status == Status.BUSY){
            delay = STATUS_ON_DELAY;
        }else
            delay = STATUS_BUSY_DELAY;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                timer.cancel();
                driverOnTime.setStatus(status);
                driverService.update(driverOnTime);

                try {
                    session.access((Command) () -> r.setValue(status));
                } catch (IllegalStateException e) {
                    System.out.println("Multithreading exception to fix " + e.getCause());
                }
            }
        }, delay);
    }

}
