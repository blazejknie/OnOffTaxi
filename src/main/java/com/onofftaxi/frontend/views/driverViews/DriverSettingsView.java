package com.onofftaxi.frontend.views.driverViews;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.AdvertisementService;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.EditPhotoDiv;
import com.onofftaxi.frontend.components.dialogs.passwordDialogs.ConfirmPasswordDialog;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Push
@Route(value = "driver/settings")
@HtmlImport("frontend://styles/custom-dialog-styles.html")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DriverSettingsView extends Div {

    private DriverView driverView;
    private DriverDto driver;
    private TextFieldsOperator textFieldsOperator;
    private MainView mainView;
    private TextField displayedName, companyName, nameField, surnameField, email, type_city, type_plate, type_nip,
            type_regon, type_postcode, type_street;
    private DriverService driverService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DriverSettingsView(@Autowired DriverService driverService, @Autowired TextFieldsOperator textFieldsOperator,
                              @Autowired AdvertisementService advertisementService) throws URISyntaxException, IOException {
        this.textFieldsOperator = textFieldsOperator;
        this.driverService = driverService;
        if (driverService.getDriver().isPresent()) {
            this.driver = driverService.getDriver().get();
        }
        setBinders(driver);
        driverView = new DriverView(driverService, advertisementService);
        mainView = new MainView(driverService, advertisementService);
        setClassName("main-layout");

        add(NavBarOperator.getMainNavBar(driverService));
        add(mainView.getAdField(advertisementService));
        add(myAccount());

        add(driverView.getMainButtons());
        add(mainView.getAdField(advertisementService));
        add(mainView.getFooter());
    }

    private void setBinders(DriverDto driver) {
        textFieldsOperator.setDriverBinderBean(driver);
    }

    private VerticalLayout myAccount() throws IOException {
        HorizontalLayout mainHorizontalLayout = new HorizontalLayout();
        VerticalLayout hlayout = new VerticalLayout();
        hlayout.setClassName("hlayout");

        VerticalLayout secondColumnLayout = new VerticalLayout();
        secondColumnLayout.setClassName("account-view-content");
        VerticalLayout firstColumnLayout = new VerticalLayout();
        firstColumnLayout.setClassName("account-view-content");
        mainHorizontalLayout.add(firstColumnLayout, secondColumnLayout);
        mainHorizontalLayout.setClassName("account-view");

        hlayout.add(mainHorizontalLayout);


        companyName = textFieldsOperator.getCompanyName();
        nameField = textFieldsOperator.getDriverName();
        surnameField = textFieldsOperator.getSurname();
        displayedName = textFieldsOperator.getDisplayedName();
        email = textFieldsOperator.getEmail();
        type_regon = textFieldsOperator.getRegon();
        type_nip = textFieldsOperator.getNip();
        type_plate = textFieldsOperator.getLicenceNumber();
        type_city = textFieldsOperator.getCity();
        type_street = textFieldsOperator.getStreet();
        type_postcode = textFieldsOperator.getPostcode();
        setTextFieldsClassName(displayedName, companyName, nameField, surnameField, email,
                type_plate, type_city, type_nip, type_regon, type_postcode, type_street);
        setTextFieldsEnabled(false, displayedName, companyName, nameField, surnameField, email,
                type_plate, type_city, type_nip, type_regon, type_postcode, type_street);
        setTextFieldsVisibility(false, companyName, nameField, surnameField, type_city, type_nip,
                type_regon, type_postcode, type_street);

        textFieldsOperator.setBindingForCompanyName();
        textFieldsOperator.setBindingForDriverName();
        textFieldsOperator.setBindingForDriverSurname();
        textFieldsOperator.setBindingForDisplayedName();
        textFieldsOperator.setBindingForRegisterEmail();
        textFieldsOperator.setBindingForRegon();
        textFieldsOperator.setBindingForNip();
        textFieldsOperator.setBindingForLicenceNumber();
        textFieldsOperator.setBindingForCityName();
        textFieldsOperator.setBindingForStreet();
        textFieldsOperator.setBindingForPostcode();


        Button deleteAccount = new Button("Usuń konto", new Icon(VaadinIcon.FILE_REMOVE));
        deleteAccount.setClassName("buttons");

        deleteAccount.addClickListener(e -> new ConfirmPasswordDialog(e.getSource().getText(),textFieldsOperator,passwordEncoder,driver,driverService).open());

        Button newPassword = new Button("Zmiana hasła", new Icon(VaadinIcon.KEY));
        newPassword.setClassName("buttons");

        newPassword.addClickListener(e -> new ConfirmPasswordDialog(e.getSource().getText(),textFieldsOperator,passwordEncoder,driver,driverService).open());

        Button saveChanges = new Button("Zapisz", new Icon(VaadinIcon.CHECK_CIRCLE));
        saveChanges.setClassName("buttons");
        saveChanges.setVisible(false);
        Button modifyButton = new Button("EDYTUJ", new Icon(VaadinIcon.EDIT));
        modifyButton.setClassName("buttons");

        Button showFactureData = new Button("rozwiń dane działalności", new Icon(VaadinIcon.ARROW_CIRCLE_DOWN));
        showFactureData.setIconAfterText(true);
        showFactureData.setClassName("show-hide-buttons");
        Button hideFactureData = new Button("schowaj dane działalności", new Icon(VaadinIcon.ARROW_CIRCLE_UP));
        hideFactureData.setIconAfterText(true);
        hideFactureData.setClassName("show-hide-buttons");
        hideFactureData.setVisible(false);
        EditPhotoDiv editPhotoDiv = new EditPhotoDiv(driverService, getUpload());

        showFactureData.addClickListener(e -> {
            setTextFieldsVisibility(true, companyName, nameField, surnameField, type_city, type_nip,
                    type_regon, type_postcode, type_street);
            hideFactureData.setVisible(true);
            showFactureData.setVisible(false);
            editPhotoDiv.setVisible(false);
        });
        hideFactureData.addClickListener(event -> {
            setTextFieldsVisibility(false, companyName, nameField, surnameField, type_city, type_nip,
                    type_regon, type_postcode, type_street);
            hideFactureData.setVisible(false);
            showFactureData.setVisible(true);
            editPhotoDiv.setVisible(true);

        });
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addClassName("buttons-layout");
        actionButtonsLayout.add(deleteAccount, newPassword, modifyButton, saveChanges);
        hlayout.add(actionButtonsLayout);

        secondColumnLayout.add(nameField, surnameField,
                companyName, type_nip, type_regon, type_postcode, type_city, type_street,
                editPhotoDiv,
                showFactureData, hideFactureData);
        firstColumnLayout.add(displayedName, email, type_plate);

        modifyButton.addClickListener(e -> {
            saveChanges.setVisible(true);
            setTextFieldsEnabled(true, displayedName, companyName, nameField, surnameField, email,
                    type_plate, type_city, type_nip, type_regon, type_postcode, type_street);
            modifyButton.setVisible(false);
        });

        saveChanges.addClickListener(e -> {
            if (textFieldsOperator.getDriverBinder().validate().isOk()) {
                driverService.update(driver);
                UI.getCurrent().getPage().reload();
            } else {
                Notification.show("Podaj poprawne dane");
            }
        });

        return hlayout;
    }

    private void setTextFieldsVisibility(boolean isVisible, TextField... textFields) {
        Arrays.stream(textFields).forEach(textField -> textField.setVisible(isVisible));
    }

    private void setTextFieldsEnabled(boolean isEnabled, TextField... textFields) {
        Arrays.stream(textFields).forEach(textField -> textField.setEnabled(isEnabled));
    }

    private void setTextFieldsClassName(TextField... textFields) {
        Arrays.stream(textFields).forEach(textField -> textField.setClassName("textfields"));
    }

    private static final String DRIVER_IMG_DIR = "images/drivers/";

    @Value("${ftp.address}")
    String ftpAddress;

    @Value("${ftp.port}")
    int ftpPort;

    @Value("${ftp.login}")
    String ftpLogin;

    @Value("${ftp.password}")
    String ftpPassword;

    @Value("${ftp.basedir}")
    String ftpRoot;

    public Upload getUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setId("lumo-upload");
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.addSucceededListener(e -> {
            String filename = e.getFileName().replaceAll(".*\\.", System.currentTimeMillis() + ".");
            FTPClient client = new FTPClient();
            try {
                client.connect(ftpAddress, ftpPort);
                client.setFileType(FTP.BINARY_FILE_TYPE);
                client.login(ftpLogin, ftpPassword);
                client.storeFile(ftpRoot + DRIVER_IMG_DIR + filename, buffer.getInputStream());
                client.logout();
                UI.getCurrent().getPage().executeJavaScript("alert('upload finished')");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return upload;
    }

}
