package com.onofftaxi.frontend.views.footerPages;

import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Push
@Route("regulamin")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class StatutView extends Div {

    public StatutView(@Autowired DriverService driverService, @Autowired AdvertisementService advertisementService) {

        MainView mainView = new MainView(driverService, advertisementService);
        VerticalLayout contactLayout = new VerticalLayout();
        setClassName("main-layout");
        NavBarOperator navBarOperator = new NavBarOperator();

        contactLayout.add(navBarOperator.getNavigationBar(driverService));
        contactLayout.add(mainView.getAdField(advertisementService));
        contactLayout.add(
                new Div(new Icon(VaadinIcon.BOOK), new Label("  Regulamin "),
                        new PdfAnchors("/frontend/Regulamin.pdf")),
                new Div(new Icon(VaadinIcon.CASH), new Label("  Warunki reklamowe "),
                        new PdfAnchors("/frontend/Advr.pdf"))
        );

        contactLayout.add(mainView.getAdField(advertisementService));
        contactLayout.add(mainView.getFooter());
        add(contactLayout);
    }

    private class PdfAnchors extends Anchor {
        PdfAnchors(String href) {
            super(href,"możesz pobrać tutaj");
            this.setTarget();
        }

        void setTarget() {
            super.setTarget("_blank");
        }
    }
}
