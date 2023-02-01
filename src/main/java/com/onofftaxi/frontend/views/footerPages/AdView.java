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
@Route("reklama")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class AdView extends Div {

    public AdView(@Autowired DriverService driverService,@Autowired AdvertisementService advertisementService) {

        MainView mainView = new MainView(driverService, advertisementService);
        VerticalLayout layout = new VerticalLayout();
        setClassName("main-layout");
        NavBarOperator navBarOperator = new NavBarOperator();

        layout.add(navBarOperator.getNavigationBar(driverService));
        layout.add(mainView.getAdField(advertisementService));
        layout.add(getContent());

        layout.add(mainView.getAdField(advertisementService));
        layout.add(mainView.getFooter());
        add(layout);
    }

    private VerticalLayout getContent() {
        VerticalLayout layout = new VerticalLayout();

        layout.add(new Label(""));
        layout.add(new Label("Zachęcamy do kontaktu w spawie reklamy w serwisie onofftaxi.pl. " +
                "Oferujemy reklamę w postaci Web Bannerów. Do Web Banneru może zostać podpięty link, który będzie przekierowywać do strony, sklepu bądź fanpage na Facebook.com reklamodawcy. " +
                "Oferujemy również reklamę na wizytówkach. Awers to reklama Waszej Firmy, a rewers to reklama serwisu onofftaxi.pl\n" +
                "(ta forma reklamy po wcześniejszym uzgodnieniu współpracy będzie możliwa i skalowana od obranego docelowego odbiorcy:\n" +

                "hotele, restauracje, pensjonaty, sklepy oraz osoby prywatne na wybranym terenie - województwo, powiat, gmina)."));
        layout.add(new Label("Zapytania prosimy kierować na adres email bądź dzwoniąc pod numer telefonu:"));
        layout.add(new Label("kontakt@onofftaxi.pl"));
        layout.add(new Label("+48 504377288"));
        layout.add(new Label(""));
        return layout;
    }
}
