package com.onofftaxi.frontend.views.footerPages;


import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Push
@Route("contact")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class ContactView extends Div {

    public ContactView(@Autowired DriverService driverService,@Autowired AdvertisementService advertisementService) {

        MainView mainView = new MainView(driverService, advertisementService);
        VerticalLayout contactLayout = new VerticalLayout();
        setClassName("main-layout");
        NavBarOperator navBarOperator = new NavBarOperator();

        contactLayout.add(navBarOperator.getNavigationBar(driverService));

        contactLayout.add(mainView.getAdField(advertisementService));
        contactLayout.add(getContact());

        contactLayout.add(mainView.getAdField(advertisementService));
        contactLayout.add(mainView.getFooter());
        add(contactLayout);
    }

    private VerticalLayout getContact() {
        VerticalLayout contactLayout = new VerticalLayout();

        contactLayout.add(new Label(""));
        contactLayout.add(new Label(""));

        contactLayout.add(new Label("Uwagi oraz spostrzeżenia dotyczące działania serwisu onofftaxi.pl prosimy kierować na poniższy adres email." +
                " W sprawie reklamy zachęcamy do skorzystania z zakładki Reklama."));
        contactLayout.add(new Label("kontakt@onofftaxi.pl"));
        contactLayout.add(new Label("Zapraszamy do kontaktu"));
        contactLayout.add(new Label(""));

        return contactLayout;
    }
}
