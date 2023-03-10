package com.onofftaxi.frontend.operators;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.AreaField;
import com.onofftaxi.frontend.components.validators.WordsValidator;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@Component
@UIScope
@StyleSheet("frontend://styles/register-style.css")
@HtmlImport("frontend://styles/shared-styles.html")
@JavaScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyArNsJPo15MeDI2UDtSuRnG2YdhF_SM6Ts&libraries=places")
public class TextFieldsOperator {
    private Binder<DriverDto> driverBinder;
    private AreaField areaField = new AreaField();
    private TextField login, email, phoneNumber, driverName, surname, companyName, regon,
            nip, city, street, postcode, operativePostCode, operativeCityName, displayedName, licenceNumber;
    private TextArea description;
    private PasswordField password, confirmPassword;
    private DriverService driverService;
    private DriverDto loggedDriver;

    public TextFieldsOperator(@Autowired DriverService driverService) {
        this.driverService = driverService;
        driverBinder = new Binder<>();
        driverBinder.setBean(new DriverDto());
        if (driverService.getDriver().isPresent()) {
            loggedDriver = driverService.getDriver().get();
        }
        login = new TextField("Login", "*Podaj login");
        email = new TextField("Email", "*Podaj email");
        phoneNumber = new TextField("Numer telefonu", "*Podaj numer telefonu");
        driverName = new TextField("Imi??", "*Podaj imi??");
        surname = new TextField("Nazwisko", "*Podaj nazwisko");
        companyName = new TextField("Nazwa dzia??alno??ci gospodarczej", "*Podaj nazw?? firmy");
        regon = new TextField("REGON", "Podaj REGON je??li posiadasz");
        nip = new TextField("NIP", "*Podaj NIP");
        city = new TextField("Adres: Miejscowo????", "*Miejscowo????");
        street = new TextField("Adres: Ulica oraz numer lokalu", "*Ulica oraz numer lokalu");
        postcode = new TextField("Adres: Kod pocztowy(00-000)", "*Kod pocztowy");
        operativePostCode = new TextField("Kod pocztowy", "*Podaj kod pocztowy");
        displayedName = new TextField("Twoja nazwa w serwisie onofftaxi.pl", "*Podaj nazw??");
        licenceNumber = new TextField("Numer licencji taxi", "*Podaj numer licencji");
        description = new TextArea("Ustaw opis: ", "np. przyjmuj?? p??atno??ci kart??, realizuj?? i dowo????" +
                " zakupy, przew??z dokument??w, holowanie, dow??z paliwa, dow??z dzieci do szko??y");

        setPasswordTextfield();
        setConfirmPasswordField();
        getOperativeCityNameField();
        setIdForTextFields();
    }

    private void setIdForTextFields() {
        login.setId("loginTxtFld");
        email.setId("emailTxtFld");
        phoneNumber.setId("phoneTxtFld");
        driverName.setId("nameTxtFld");
        surname.setId("surnameTxtFld");
        companyName.setId("comNameTxtFld");
        regon.setId("regonTxtFld");
        nip.setId("nipTxtFld");
        city.setId("cityTxtFld");
        street.setId("streetTxtFld");
        postcode.setId("postTxtFld");
        operativePostCode.setId("operativePostCodeTxtFld");
        displayedName.setId("dispNameTxtFld");
        licenceNumber.setId("licenceNrTxtFld");
        password.setId("passwordTxtFld");
        confirmPassword.setId("confPasswTxtFld");
        operativeCityName.setId("operCityNameTxtFld");
        description.setId("descriptionTxtAra");
    }

    public void setDriverBinderBean(DriverDto driver) {
        driverBinder.setBean(driver);
    }




    private void setPasswordTextfield() {
        password = new PasswordField("Has??o", "*Podaj has??o");
        password.setClassName("register-fields");
    }

    private void setConfirmPasswordField() {
        confirmPassword = new PasswordField("Potwierd?? has??o", "*Potwierd?? has??o");
        confirmPassword.setClassName("register-fields");
    }


    private void getOperativeCityNameField() {
        operativeCityName = areaField.getContent();
        operativeCityName.setPlaceholder("*Podaj miejscowo???? licencji taxi.");
        operativeCityName.setClearButtonVisible(true);
        operativeCityName.setLabel("Miejscowo???? (licencja taxi)");
    }

    public void setBindingForCredentials() throws IOException {
        driverBinder.forField(login).withValidator(
                login -> login.length() >= 4 && login.length() <= 20
                , "Liczba znak??w dla loginu od 4 do 20.")
                .withValidator(x -> driverService.getByLogin(x) == null, "Podany login jest ju?? zaj??ty, prosimy o podanie innego loginu.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getLogin, DriverDto::setLogin);
        setBindingForPassword();
    }

    public void setBindingForPassword() {
        driverBinder.forField(password)
                .withValidator(pass -> pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$"),
                        "Has??o musi posiada?? przynajmniej 8 znak??w, jedn?? wielk?? i ma???? liter?? oraz jedn?? cyfr??")
                .bind(DriverDto::getPassword, DriverDto::setPassword);
    }


    public void setBindingForConfirmPassword() {
        driverBinder.forField(confirmPassword).withValidator(confirm_password -> confirm_password.equals(password.getValue()),
                "Has??a nie s?? identyczne.")
                .bind(DriverDto::getPassword, DriverDto::setPassword);
    }

    public void setBindingForRegisterPhoneNumber() {
        driverBinder.forField(phoneNumber)

                .withValidator(phone -> phone.matches("^\\d{9}$"), "Prosz?? wpisa?? 9-cyfrowy numer telefonu")
                .withValidator(phone -> driverService.getByPhoneNumber(phone) == null, "Podany numer telefonu jest ju?? zaj??ty.")

                .bind(DriverDto::getPhone, DriverDto::setPhone);
    }

    public void setBindingForEditPhoneNumber() {
        driverBinder.forField(phoneNumber)
                .withValidator(phone -> phone.matches("^\\d{9}$"), "Prosz?? wpisa?? 9-cyfrowy numer telefonu")
                .withValidator(phone ->
                        driverService.getByPhoneNumber(phone) == null ||

                                phone.equalsIgnoreCase(driverService.getDriver().get().getPhone()), "Podany numer telefonu jest ju?? zaj??ty.")
                .bind(DriverDto::getPhone, DriverDto::setPhone);
    }

    public void setBindingForRegisterEmail() {
        driverBinder.forField(email)
                .withValidator(new EmailValidator("Prosz?? wpisa?? poprawny email"))

                .withValidator(email -> !Optional.ofNullable(driverService.getByEmail(email)).isPresent() ||
                        Optional.ofNullable(loggedDriver).isPresent() &&
                        driverService.getByEmail(email).getEmail().equalsIgnoreCase(loggedDriver.getEmail())
                        , "Podany e-mail jest ju?? zaj??ty.")

                .bind(DriverDto::getEmail, DriverDto::setEmail);
    }

    public void setBindingForEditEmail() {
        driverBinder.forField(email)
                .withValidator(new EmailValidator("Prosz?? wpisa?? poprawny e-mail"))
                .withValidator(email -> driverService.getByEmail(email) == null ||
                                email.equalsIgnoreCase(loggedDriver.getEmail()),
                        "Podany e-mail jest ju?? zaj??ty.")
                .bind(DriverDto::getEmail, DriverDto::setEmail);
    }

    public void setBindingForDriverName() throws IOException {
        driverBinder.forField(driverName).withValidator(driverName -> driverName.length() > 2 && driverName.length() <= 50, "Prosz?? wpisa?? poprawnie imi??.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getFirstName, DriverDto::setFirstName);
    }

    public void setBindingForDriverSurname() throws IOException {
        driverBinder.forField(surname).withValidator(driverName -> driverName.length() > 1 && driverName.length() <= 50, "Prosz?? wpisa?? poprawnie nazwisko.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getLastName, DriverDto::setLastName);
    }

    public void setBindingForCompanyName() throws IOException {
        driverBinder.forField(companyName).withValidator(companyName -> companyName.length() > 3 && companyName.length() <= 255, "Prosz?? poprawnie uzupe??ni?? nazw?? firmy.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getCompany, DriverDto::setCompany);
    }

    public void setBindingForRegon() {
        driverBinder.forField(regon).withValidator(regon -> regon.length()==0 || regon.matches("^[0-9]{9,9}$"), "Prosz?? porawnie uzupe??ni?? numer REGON.")
                .bind(DriverDto::getRegon, DriverDto::setRegon);
    }
    public void setBindingForNip() {
        driverBinder.forField(nip).withValidator(nip -> nip.matches("^[0-9]{10,10}$"), "Prosz?? porawnie uzupe??ni?? numer NIP.")
                .bind(DriverDto::getNip, DriverDto::setNip);
    }

    public void setBindingForCityName() throws IOException {
        driverBinder.forField(city).withValidator(location -> location.length() > 2 && location.length() <= 50, "Prosz?? wpisa?? poprawn?? nazw?? miejsowo??ci.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getCity, DriverDto::setCity);
    }

    public void setBindingForStreet() throws IOException {
        driverBinder.forField(street).withValidator(street -> street.length() > 2 && street.length() <= 100, "Prosz?? wpisa?? poprawn?? nazw?? ulicy.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getStreet, DriverDto::setStreet);
    }

    public void setBindingForPostcode() {
        driverBinder.forField(postcode).withValidator(postcode -> postcode.matches("^\\d{2}-\\d{3}$"),
                "Prosz?? wpisa?? poprawny kod pocztowy.")
                .bind(DriverDto::getPostcode, DriverDto::setPostcode);
    }

    public void setBindingForDescription() {
        try {
            driverBinder.forField(description).withValidator(desc -> desc.length() <= 100,
                    "Maksymalna liczba znak??w dla opisu to 100")
                    .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                    .bind(DriverDto::getDescription, DriverDto::setDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBindingForDisplayedName() throws IOException {
        driverBinder.forField(displayedName)
                .withValidator(s -> s.length() >= 4 && s.length() <= 50, "Wymagana liczba znak??w: min. 4 -maks. 50.")
                .withValidator(displayedName ->
                                !Optional.ofNullable(driverService.getByDisplayedName(displayedName)).isPresent() ||
                                        Optional.ofNullable(loggedDriver).isPresent() &&
                                        driverService.getByDisplayedName(displayedName).getDisplayedName()
                                        .equalsIgnoreCase(loggedDriver.getDisplayedName()),
                        "Podana nazwa jest juz zaj??ta.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getDisplayedName, DriverDto::setDisplayedName);
    }

    public void setBindingForOperativeCityName() throws IOException {
        driverBinder.forField(operativeCityName).withValidator(x -> x.length() >= 2 && x.length() <= 30, "Prosz?? wpisa?? prawid??ow?? nazw?? miejscowo??ci.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne s??owa"))
                .bind(DriverDto::getPlaceName, DriverDto::setPlaceName);
    }

    public void setBindingForOperativePostCode() {
        driverBinder.forField(operativePostCode).withValidator(code -> code.matches("\\d{2}-\\d{3}"), "Prosz?? wpisa?? prawid??owy kod pocztowy(00-000).")
                .bind(DriverDto::getPostcode, DriverDto::setPostcode);
    }

    public void setBindingForLicenceNumber() {
        driverBinder.forField(licenceNumber).withValidator(s -> s.length() > 2 && s.length() < 20,
                "Prosz?? wpisa?? prawid??owy numer licencji.")
                .bind(DriverDto::getLicense, DriverDto::setLicense);
    }

    public void removeBindings(TextField... textFields) {
        Arrays.stream(textFields).forEach(textField -> driverBinder.removeBinding(textField));
    }

    public void removePasswordBinding() {
        driverBinder.removeBinding(password);
        driverBinder.removeBinding(confirmPassword);
    }

    public void removeBindingsForSecondFields() {
        removeBindings(driverName, surname, companyName, regon, nip, city, street, postcode);
    }

    public void removeBindingsForThirdFields() {
        removeBindings(displayedName, licenceNumber);
    }

    public void setAllTextFieldsClassName(String className) {
        List<TextField> textFields = Arrays.asList(login, email, phoneNumber, driverName, surname, companyName, regon,
                nip, city, street, postcode, operativePostCode, operativeCityName, displayedName, licenceNumber);
        textFields.forEach(x -> x.setClassName(className));
    }

    public void setClassNameForTextFields(String className, TextField... textFields) {
        Arrays.stream(textFields).forEach(x -> x.setClassName(className));
    }

}
