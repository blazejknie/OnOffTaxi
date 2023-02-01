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
        driverName = new TextField("Imię", "*Podaj imię");
        surname = new TextField("Nazwisko", "*Podaj nazwisko");
        companyName = new TextField("Nazwa działalności gospodarczej", "*Podaj nazwę firmy");
        regon = new TextField("REGON", "Podaj REGON jeśli posiadasz");
        nip = new TextField("NIP", "*Podaj NIP");
        city = new TextField("Adres: Miejscowość", "*Miejscowość");
        street = new TextField("Adres: Ulica oraz numer lokalu", "*Ulica oraz numer lokalu");
        postcode = new TextField("Adres: Kod pocztowy(00-000)", "*Kod pocztowy");
        operativePostCode = new TextField("Kod pocztowy", "*Podaj kod pocztowy");
        displayedName = new TextField("Twoja nazwa w serwisie onofftaxi.pl", "*Podaj nazwę");
        licenceNumber = new TextField("Numer licencji taxi", "*Podaj numer licencji");
        description = new TextArea("Ustaw opis: ", "np. przyjmuję płatności kartą, realizuję i dowożę" +
                " zakupy, przewóz dokumentów, holowanie, dowóz paliwa, dowóz dzieci do szkoły");

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
        password = new PasswordField("Hasło", "*Podaj hasło");
        password.setClassName("register-fields");
    }

    private void setConfirmPasswordField() {
        confirmPassword = new PasswordField("Potwierdź hasło", "*Potwierdź hasło");
        confirmPassword.setClassName("register-fields");
    }


    private void getOperativeCityNameField() {
        operativeCityName = areaField.getContent();
        operativeCityName.setPlaceholder("*Podaj miejscowość licencji taxi.");
        operativeCityName.setClearButtonVisible(true);
        operativeCityName.setLabel("Miejscowość (licencja taxi)");
    }

    public void setBindingForCredentials() throws IOException {
        driverBinder.forField(login).withValidator(
                login -> login.length() >= 4 && login.length() <= 20
                , "Liczba znaków dla loginu od 4 do 20.")
                .withValidator(x -> driverService.getByLogin(x) == null, "Podany login jest już zajęty, prosimy o podanie innego loginu.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getLogin, DriverDto::setLogin);
        setBindingForPassword();
    }

    public void setBindingForPassword() {
        driverBinder.forField(password)
                .withValidator(pass -> pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$"),
                        "Hasło musi posiadać przynajmniej 8 znaków, jedną wielką i małą literę oraz jedną cyfrę")
                .bind(DriverDto::getPassword, DriverDto::setPassword);
    }


    public void setBindingForConfirmPassword() {
        driverBinder.forField(confirmPassword).withValidator(confirm_password -> confirm_password.equals(password.getValue()),
                "Hasła nie są identyczne.")
                .bind(DriverDto::getPassword, DriverDto::setPassword);
    }

    public void setBindingForRegisterPhoneNumber() {
        driverBinder.forField(phoneNumber)

                .withValidator(phone -> phone.matches("^\\d{9}$"), "Proszę wpisać 9-cyfrowy numer telefonu")
                .withValidator(phone -> driverService.getByPhoneNumber(phone) == null, "Podany numer telefonu jest już zajęty.")

                .bind(DriverDto::getPhone, DriverDto::setPhone);
    }

    public void setBindingForEditPhoneNumber() {
        driverBinder.forField(phoneNumber)
                .withValidator(phone -> phone.matches("^\\d{9}$"), "Proszę wpisać 9-cyfrowy numer telefonu")
                .withValidator(phone ->
                        driverService.getByPhoneNumber(phone) == null ||

                                phone.equalsIgnoreCase(driverService.getDriver().get().getPhone()), "Podany numer telefonu jest już zajęty.")
                .bind(DriverDto::getPhone, DriverDto::setPhone);
    }

    public void setBindingForRegisterEmail() {
        driverBinder.forField(email)
                .withValidator(new EmailValidator("Proszę wpisać poprawny email"))

                .withValidator(email -> !Optional.ofNullable(driverService.getByEmail(email)).isPresent() ||
                        Optional.ofNullable(loggedDriver).isPresent() &&
                        driverService.getByEmail(email).getEmail().equalsIgnoreCase(loggedDriver.getEmail())
                        , "Podany e-mail jest już zajęty.")

                .bind(DriverDto::getEmail, DriverDto::setEmail);
    }

    public void setBindingForEditEmail() {
        driverBinder.forField(email)
                .withValidator(new EmailValidator("Proszę wpisać poprawny e-mail"))
                .withValidator(email -> driverService.getByEmail(email) == null ||
                                email.equalsIgnoreCase(loggedDriver.getEmail()),
                        "Podany e-mail jest już zajęty.")
                .bind(DriverDto::getEmail, DriverDto::setEmail);
    }

    public void setBindingForDriverName() throws IOException {
        driverBinder.forField(driverName).withValidator(driverName -> driverName.length() > 2 && driverName.length() <= 50, "Proszę wpisać poprawnie imię.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getFirstName, DriverDto::setFirstName);
    }

    public void setBindingForDriverSurname() throws IOException {
        driverBinder.forField(surname).withValidator(driverName -> driverName.length() > 1 && driverName.length() <= 50, "Proszę wpisać poprawnie nazwisko.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getLastName, DriverDto::setLastName);
    }

    public void setBindingForCompanyName() throws IOException {
        driverBinder.forField(companyName).withValidator(companyName -> companyName.length() > 3 && companyName.length() <= 255, "Proszę poprawnie uzupełnić nazwę firmy.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getCompany, DriverDto::setCompany);
    }

    public void setBindingForRegon() {
        driverBinder.forField(regon).withValidator(regon -> regon.length()==0 || regon.matches("^[0-9]{9,9}$"), "Proszę porawnie uzupełnić numer REGON.")
                .bind(DriverDto::getRegon, DriverDto::setRegon);
    }
    public void setBindingForNip() {
        driverBinder.forField(nip).withValidator(nip -> nip.matches("^[0-9]{10,10}$"), "Proszę porawnie uzupełnić numer NIP.")
                .bind(DriverDto::getNip, DriverDto::setNip);
    }

    public void setBindingForCityName() throws IOException {
        driverBinder.forField(city).withValidator(location -> location.length() > 2 && location.length() <= 50, "Proszę wpisać poprawną nazwę miejsowości.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getCity, DriverDto::setCity);
    }

    public void setBindingForStreet() throws IOException {
        driverBinder.forField(street).withValidator(street -> street.length() > 2 && street.length() <= 100, "Proszę wpisać poprawną nazwę ulicy.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getStreet, DriverDto::setStreet);
    }

    public void setBindingForPostcode() {
        driverBinder.forField(postcode).withValidator(postcode -> postcode.matches("^\\d{2}-\\d{3}$"),
                "Proszę wpisać poprawny kod pocztowy.")
                .bind(DriverDto::getPostcode, DriverDto::setPostcode);
    }

    public void setBindingForDescription() {
        try {
            driverBinder.forField(description).withValidator(desc -> desc.length() <= 100,
                    "Maksymalna liczba znaków dla opisu to 100")
                    .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                    .bind(DriverDto::getDescription, DriverDto::setDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBindingForDisplayedName() throws IOException {
        driverBinder.forField(displayedName)
                .withValidator(s -> s.length() >= 4 && s.length() <= 50, "Wymagana liczba znaków: min. 4 -maks. 50.")
                .withValidator(displayedName ->
                                !Optional.ofNullable(driverService.getByDisplayedName(displayedName)).isPresent() ||
                                        Optional.ofNullable(loggedDriver).isPresent() &&
                                        driverService.getByDisplayedName(displayedName).getDisplayedName()
                                        .equalsIgnoreCase(loggedDriver.getDisplayedName()),
                        "Podana nazwa jest juz zajęta.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getDisplayedName, DriverDto::setDisplayedName);
    }

    public void setBindingForOperativeCityName() throws IOException {
        driverBinder.forField(operativeCityName).withValidator(x -> x.length() >= 2 && x.length() <= 30, "Proszę wpisać prawidłową nazwę miejscowości.")
                .withValidator(new WordsValidator("Wykryto niedopuszczalne słowa"))
                .bind(DriverDto::getPlaceName, DriverDto::setPlaceName);
    }

    public void setBindingForOperativePostCode() {
        driverBinder.forField(operativePostCode).withValidator(code -> code.matches("\\d{2}-\\d{3}"), "Proszę wpisać prawidłowy kod pocztowy(00-000).")
                .bind(DriverDto::getPostcode, DriverDto::setPostcode);
    }

    public void setBindingForLicenceNumber() {
        driverBinder.forField(licenceNumber).withValidator(s -> s.length() > 2 && s.length() < 20,
                "Proszę wpisać prawidłowy numer licencji.")
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
