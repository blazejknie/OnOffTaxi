package com.onofftaxi.frontend.views.loginRegisterViews;

import com.onofftaxi.backend.model.*;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.backend.service.EmailSenderService;
import com.onofftaxi.frontend.components.AreaField;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@JavaScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyArNsJPo15MeDI2UDtSuRnG2YdhF_SM6Ts&libraries=places")

@Route(value = "register")
@StyleSheet("frontend://styles/register-style.css")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DriverRegisterView extends FormLayout {

    private TextField operativePostCode, operativeCityName, displayedName, licenceNumber;
    private Checkbox acceptPrivacyPolitics, acceptTermsCheckbox;
    private TextField type_email, type_phoneNumber, type_login;
    private PasswordField type_password, confirm_password;
    private DriverDto driver;
    private Binder<DriverDto> binder;
    private VerticalLayout firstAccordion, secondAccordion, thirdAccordion;
    private TextFieldsOperator textFieldsOperator;
    private DriverService driverService;
    private Button goToSecondStepButton, goToThirdStepButton, registerButton;
    private AreaField areaField;
    private Checkbox acceptTermsAndPolicyCheckbox, acceptMail;

    public DriverRegisterView(@Autowired TextFieldsOperator textFieldsOperator,
                              @Autowired DriverService driverService,
                              @Autowired EmailSenderService emailSenderService) throws IOException {
        if (driverService.getDriver().isPresent()) {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/driver/account\", \"_self\");");
        } else {

            this.binder = textFieldsOperator.getDriverBinder();
            this.driverService = driverService;
            this.driver = binder.getBean();
            this.areaField = textFieldsOperator.getAreaField();
            this.textFieldsOperator = textFieldsOperator;
            setClassName("register-layout");
            firstAccordion = getFirstAccordion();
            secondAccordion = getSecondAccordion();
            thirdAccordion = getThirdAccordion(emailSenderService);
            textFieldsOperator.setAllTextFieldsClassName("register-fields");
            binder.addValueChangeListener(e -> {
                if (binder.isValid()) {
                    goToSecondStepButton.setEnabled(true);
                    goToSecondStepButton.setClassName("register-next-button");
                    goToThirdStepButton.setEnabled(true);
                    goToThirdStepButton.setClassName("register-next-button");
                    registerButton.setEnabled(true);
                    registerButton.setClassName("register-next-button");
                }
            });
            add(firstAccordion);

        }
    }

    public VerticalLayout getFirstAccordion() throws IOException {
        Label mainLabel = new Label("REJESTRACJA 1/3");
        Label infoLabel = new Label("dane dotyczące logowania");
        infoLabel.setClassName("register-labels");
        mainLabel.setClassName("register-labels");
        Label requiredLabel = new Label("* - pola wymagane");
        requiredLabel.setClassName("register-labels");
        mainLabel.setEnabled(false);
        VerticalLayout registerLayout = new VerticalLayout();
        registerLayout.setClassName("registration-labeltext");

        type_login = textFieldsOperator.getLogin();
        type_email = textFieldsOperator.getEmail();
        type_phoneNumber = textFieldsOperator.getPhoneNumber();
        type_password = textFieldsOperator.getPassword();
        confirm_password = textFieldsOperator.getConfirmPassword();
        textFieldsOperator.setBindingForRegisterEmail();
        textFieldsOperator.setBindingForRegisterPhoneNumber();
        textFieldsOperator.setBindingForCredentials();
        textFieldsOperator.setBindingForConfirmPassword();

        Div passwordConfirm = new Div(confirm_password);
        passwordConfirm.setClassName("password-confirm-div");

        goToSecondStepButton = new Button("Dalej ", new Icon(VaadinIcon.ARROW_FORWARD));
        goToSecondStepButton.setClassName("register-next-button");
        goToSecondStepButton.setId("reg-first-btn");
        goToSecondStepButton.setIconAfterText(true);
        goToSecondStepButton.setEnabled(false);
        goToSecondStepButton.setClassName("register-next-button-disabled");
        goToSecondStepButton.addClickListener(e -> {
            if (!type_password.getValue().equals(confirm_password.getValue())) {
                Notification.show("Hasło jest nieporawne");
//            } else if (driverService.getByLogin(login.getValue())!=null) {
//                Notification.show("login jest zajęty!");
//            } else if (driverService.getByEmail(email.getValue()) != null) {
//                Notification.show("Konto z podanym e-mailem już istnieje!");
//            } else if (driverService.getByPhoneNumber(phoneNumber.getValue()) != null) {
//                Notification.show("Konto z podanym numerem telefonu już istnieje!");
            } else {
                try {

                    if (binder.validate().isOk()) {
                        textFieldsOperator.setBindingForDriverName();
                        textFieldsOperator.setBindingForDriverSurname();
                        textFieldsOperator.setBindingForCompanyName();
                        textFieldsOperator.setBindingForRegon();
                        textFieldsOperator.setBindingForNip();
                        textFieldsOperator.setBindingForCityName();
                        textFieldsOperator.setBindingForStreet();
                        textFieldsOperator.setBindingForPostcode();
                        add(secondAccordion);

                        remove(firstAccordion);
                        if (!binder.isValid()) {
                            goToThirdStepButton.setEnabled(false);
                            goToThirdStepButton.setClassName("register-next-button-disabled");
                        }
                    } else {
                        Notification.show("Podaj poprawne dane");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        Button back = new Button("Powrót", new Icon(VaadinIcon.ARROW_BACKWARD));
        back.setClassName("register-back-button");
        back.addClickListener(event -> UI.getCurrent().getPage().executeJavaScript("window.open(\"/\", \"_self\");"));

        Div navigateButtonsDiv = new Div(back, goToSecondStepButton);
        navigateButtonsDiv.setClassName("navigate-buttons-div");
        registerLayout.add(mainLabel, infoLabel,requiredLabel, type_login, type_email, type_phoneNumber, type_password, confirm_password,
                navigateButtonsDiv);

        return registerLayout;
    }

    private VerticalLayout getSecondAccordion() {
        Label mainLabel = new Label("REJESTRACJA 2/3");
        Label infoLabel = new Label("dane działalności gospodarczej");

        mainLabel.setClassName("register-labels");
        infoLabel.setClassName("register-labels");

        VerticalLayout registerLayout = new VerticalLayout();

        TextField type_driverName = textFieldsOperator.getDriverName();
        TextField type_surname = textFieldsOperator.getSurname();
        TextField type_companyName = textFieldsOperator.getCompanyName();
        TextField type_regon = textFieldsOperator.getRegon();
        TextField type_nip = textFieldsOperator.getNip();
        TextField type_city = textFieldsOperator.getCity();
        TextField type_street = textFieldsOperator.getStreet();
        TextField type_postcode = textFieldsOperator.getPostcode();
        goToThirdStepButton = new Button("Dalej ", new Icon(VaadinIcon.ARROW_FORWARD));
        goToThirdStepButton.setClassName("register-next-button");
        goToThirdStepButton.setId("reg-second-btn");
        //todo: driver builder
        goToThirdStepButton.addClickListener(e -> {
            if (binder.validate().isOk()) {
                textFieldsOperator.setBindingForLicenceNumber();
                try {
                    textFieldsOperator.setBindingForDisplayedName();

                    textFieldsOperator.setBindingForOperativeCityName();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textFieldsOperator.setBindingForOperativePostCode();
                if (!binder.isValid()) {
                    registerButton.setEnabled(false);
                    registerButton.setClassName("register-next-button-disabled");
                }
                add(thirdAccordion);
                remove(secondAccordion);
            } else {
                Notification.show("Podaj poprawne dane");
            }
        });
        Button backwardButton = new Button("Cofnij", new Icon(VaadinIcon.ARROW_BACKWARD));
        backwardButton.setClassName("register-back-button");
        backwardButton.addClickListener(e -> {
            textFieldsOperator.removeBindings(type_driverName, type_surname, type_companyName, type_regon, type_nip,
                    type_city, type_street, type_postcode);
            add(firstAccordion);
            remove(secondAccordion);
        });

        Div navigateButtonsDiv = new Div(backwardButton, goToThirdStepButton);
        navigateButtonsDiv.setClassName("navigate-buttons-div");
        registerLayout.add(mainLabel, infoLabel, type_driverName, type_surname, type_companyName, type_nip, type_regon, type_postcode, type_city,
                type_street, navigateButtonsDiv);

        return registerLayout;
    }

    private VerticalLayout getThirdAccordion(EmailSenderService emailSenderService) {

        VerticalLayout verticalLayout = new VerticalLayout();

        Label mainLabel = new Label("REJESTRACJA 3/3");
        Label infoLabel = new Label("dane dotyczące licencji");

        mainLabel.setClassName("register-labels");
        infoLabel.setClassName("register-labels");

        operativeCityName = textFieldsOperator.getOperativeCityName();
        displayedName = textFieldsOperator.getDisplayedName();
        licenceNumber = textFieldsOperator.getLicenceNumber();

        VerticalLayout checkboxLayout = new VerticalLayout();
        acceptTermsAndPolicyCheckbox = new Checkbox();
        acceptTermsAndPolicyCheckbox.setLabelAsHtml("Akceptuję <a href='/frontend/Regulamin.pdf' target=\"_blank\">regulamin</a> i <a href='/frontend/Regulamin.pdf' target=\"_blank\">politykę prywatności</a>");
        acceptTermsAndPolicyCheckbox.setClassName("register-checkbox");

        acceptTermsCheckbox = new Checkbox("Wyrażam zgodę na przetwarzanie moich danych osobowych podanych w formularzu rejestracyjnym przez Ireneusza Senger w celu: założenia i korzystania z konta w serwisie onofftaxi.pl oraz w celach statystycznych. Zostałem poinformowany, że administratorem moich danych osobowych będzie Ireneusz Senger, ul. Krótka 1 A/2, 83-407 Łubiana, a szczegółowe zasady przetwarzania danych osobowych (w tym moje uprawnienia) opisane zostały w Polityce prywatności, która znajduje się tutaj. Podanie danych jest dobrowolne, ale niezbędne w celu założenia i korzystania z kontaserwisie onofftaxi.pl.");
        acceptTermsCheckbox.setClassName("register-checkbox");
        acceptTermsCheckbox.setId("acceptTermsChBox");
        acceptPrivacyPolitics = new Checkbox("Wyrażam zgodę na otrzymywanie informacji handlowej od Ireneusza Senger, zgodnie z ustawą z dnia 18 lipca 2002 r. o świadczeniu usług drogą elektroniczną.");
        acceptPrivacyPolitics.setClassName("register-checkbox");
        acceptPrivacyPolitics.setId("privacyChBox");
        acceptMail = new Checkbox("Wyrażam zgodę na używanie przez Ireneusza Senger telekomunikacyjnych urządzeń końcowych w postaci telefonu oraz urządzenia, którego używam do obsługi poczty elektronicznej zgodnie z art. 172 ustawy z dnia 16 lipca 2004 r. Prawo telekomunikacyjne.");
        acceptMail.setClassName("register-checkbox");

        checkboxLayout.add(acceptTermsAndPolicyCheckbox, acceptPrivacyPolitics, acceptTermsCheckbox, acceptMail);
        checkboxLayout.setClassName("register-checkbox-layout");
        checkboxLayout.setWidth(null);

        registerButton = new Button("Zarejestruj");
        registerButton.setId("drv-third-btn");
        registerButton.addClickListener(e -> {
            if (binder.validate().isOk() && isCheckedPrivacyAndTerms()) {
                savePlaceInDriverBean();
                driver.setStatus(Status.OFF);
                driverService.add(driver);
                emailSenderService.sendNewUserRegisterMessage(driver.getEmail());
                UI.getCurrent().getPage().executeJavaScript("window.open(\"/\", \"_self\");");

            } else if (!isCheckedPrivacyAndTerms()) {
                Notification.show("Regulamin i polityka prywatności wymagają akceptacji ");
            } else {
                Notification.show("Podaj poprawne dane");
            }
        });
        Button backwardButton = new Button("Cofnij", new Icon(VaadinIcon.ARROW_BACKWARD));
        backwardButton.setClassName("register-back-button");
        backwardButton.addClickListener(e -> {

            if (!binder.validate().isOk()) {
                textFieldsOperator.removeBindings(displayedName, licenceNumber, operativeCityName);
            }
            textFieldsOperator.setAreaField(new AreaField());

            add(secondAccordion);
            remove(thirdAccordion);
        });

        Div navigateButtonsDiv = new Div(backwardButton, registerButton);
        navigateButtonsDiv.setClassName("navigate-buttons-div");
        verticalLayout.add(mainLabel, infoLabel, displayedName, licenceNumber, operativeCityName,
                checkboxLayout, navigateButtonsDiv);

        return verticalLayout;
    }

    public void updateDriverBeanFromFields(TextField type_driverName, TextField type_surname, TextField type_companyName, TextField type_regon, TextField type_nip, TextField type_city, TextField type_street, TextField type_postcode) {
        driver.setCompany(type_companyName.getValue());
        driver.setNip(type_nip.getValue());
        driver.setRegon(type_regon.getValue());
        driver.setStatus(Status.OFF);
        driver.setType(UserType.DRIVER);
        driver.setFirstName(type_driverName.getValue());
        driver.setLastName(type_surname.getValue());
        driver.setPostcode(type_postcode.getValue());
        driver.setStreet(type_street.getValue());
        driver.setCity(type_city.getValue());
    }

    public void savePlaceInDriverBean() {
        driver.setLogged(false);
        Point point = areaField.getPlace().createPoint();
        driver.setPlaceLocation(point);
        driver.setPlaceDistrict(areaField.getPlace().getDistrict());
        driver.setPlaceName(areaField.getPlace().getName());
        driver.setGeolocationPosition(point);

    }

    private boolean isCheckedPrivacyAndTerms() {
        return acceptTermsAndPolicyCheckbox.getValue()
                && acceptPrivacyPolitics.getValue()
                && acceptTermsCheckbox.getValue()
                && acceptMail.getValue();
    }

}

