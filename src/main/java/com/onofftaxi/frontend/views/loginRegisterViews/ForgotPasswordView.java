package com.onofftaxi.frontend.views.loginRegisterViews;

import com.onofftaxi.backend.model.Driver;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.backend.service.EmailSenderService;
import com.onofftaxi.frontend.components.dialogs.passwordDialogs.PasswordChangeDialog;
import com.onofftaxi.frontend.operators.NavBarOperator;
import com.onofftaxi.frontend.operators.TextFieldsOperator;
import com.onofftaxi.frontend.views.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "forgotpassword")
@HtmlImport("frontend://styles/custom-dialog-styles.html")
@StyleSheet("frontend://styles/login-style.css")
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class ForgotPasswordView extends Div {


    public ForgotPasswordView(@Autowired DriverService driverService, @Autowired EmailSenderService emailSenderService,
                              @Autowired TextFieldsOperator textFieldsOperator) {
        if (driverService.getDriver().isPresent()) {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/driver/account\", \"_self\");");
        } else {
            VerticalLayout forgotLayout = new VerticalLayout();
            forgotLayout.setClassName("login-view");
            TextField emailTextfield = new TextField(null, "Podaj email");

            Binder<Driver> binder = new Binder<>();
            binder.forField(emailTextfield).withValidator(new EmailValidator("Podaj poprawny email"))
                    .bind(Driver::getEmail, Driver::setEmail);
            emailTextfield.setClassName("login-fields");
            emailTextfield.setWidthFull();
            Button sendPasswordButton = new Button("Wyślij klucz do zresetowania hasła");
            sendPasswordButton.setClassName("login-button");

            Label exparingTokenLabel = new Label("Klucza zmiany hasła należy użyć w czasie pięciu minut");
            exparingTokenLabel.setClassName("exparing-token-label");
            Button tokenButton = new Button("Wyślij");
            TextField tokenTextField = new TextField(null, "Wprowadź klucz");
            tokenTextField.setClassName("dialog-textfields");
            VerticalLayout verticalLayout = new VerticalLayout(exparingTokenLabel, tokenTextField, tokenButton);

            sendPasswordButton.addClickListener(click -> {
                String emailTextfieldValue = emailTextfield.getValue();
                try {
                    emailTextfield.setAutofocus(true);

                    emailTextfield.setClassName("dialog-textfields");
                    emailSenderService.sendForgotPasswordMessage(emailTextfieldValue, driverService);

                    tokenButton.setClassName("dialog-button");
                    Dialog tokenDialog = new Dialog(verticalLayout);
                    tokenDialog.getElement().setAttribute("theme", "custom-dialog");
                    tokenDialog.setCloseOnOutsideClick(false);
                    tokenDialog.open();
                    tokenButton.addClickListener(clicks -> {
                        if (emailSenderService.compareToken(tokenTextField.getValue())) {
                            tokenDialog.close();
                            Dialog passwordChangeDialog = new PasswordChangeDialog(textFieldsOperator,
                                    driverService.getByEmail(emailTextfieldValue),driverService);
                            passwordChangeDialog.getElement().setAttribute("theme", "custom-dialog");
                            passwordChangeDialog.open();
                            passwordChangeDialog.setCloseOnOutsideClick(false);
                        } else {
                            Notification.show("Wpisz poprawny klucz");
                        }
                    });
                } catch (Exception e) {
                    MainView.getAlert();
                }
            });

            Image ourAppLogo = NavBarOperator.getOurAppLogo();
            ourAppLogo.setClassName("login-logo");
            forgotLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, ourAppLogo, emailTextfield, sendPasswordButton);
            Button mainPageButton = new Button("powrót do strony głównej",buttonClickEvent -> {
                UI.getCurrent().getPage().executeJavaScript("window.open(\"/\", \"_self\");");
            });
            mainPageButton.setClassName("register-butAnch");
            forgotLayout.add(ourAppLogo, emailTextfield, sendPasswordButton, mainPageButton);

            setClassName("login-view");
            add(forgotLayout);
        }
    }

}
