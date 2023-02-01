package com.onofftaxi.frontend.views.footerPages;

import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Push
@Route("info")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class InfoView extends Div {

    public InfoView(@Autowired DriverService driverService, @Autowired AdvertisementService advertisementService) {

        MainView mainView = new MainView(driverService, advertisementService);
        VerticalLayout contactLayout = new VerticalLayout();
        setClassName("main-layout");
        NavBarOperator navBarOperator = new NavBarOperator();

        contactLayout.add(navBarOperator.getNavigationBar(driverService));

        contactLayout.add(mainView.getAdField(advertisementService));


        contactLayout.add(mainView.getAdField(advertisementService));
        contactLayout.add(mainView.getFooter());
        add(contactLayout);
    }
}
